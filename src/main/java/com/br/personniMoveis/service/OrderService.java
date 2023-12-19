package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.*;
import com.br.personniMoveis.dto.ProductCmp.ProductCmpDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.exception.ConflictException;
import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.mapper.ProductCmp.ProductCmpMapper;
import com.br.personniMoveis.model.product.Option;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.product.Section;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.OptionCmp;
import com.br.personniMoveis.model.productCmp.ProductCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import com.br.personniMoveis.model.user.*;
import com.br.personniMoveis.repository.*;
import com.br.personniMoveis.service.payment.PaymentService;
import com.br.personniMoveis.service.product.ProductService;
import com.br.personniMoveis.service.productCmp.ProductCmpService;
import com.br.personniMoveis.utils.AuthUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderCmpRepository orderCmpRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemCmpRepository orderItemCmpRepository;
    private final ProductService productService;
    private final UserService userService;
    private final PaymentService paymentService;
    private final OptionRepository optionRepository;
    private final AuthUtils authUtils;
    private final ProductCmpService productCmpService;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderCmpRepository orderCmpRepository,
                        OrderItemRepository orderItemRepository, OrderItemCmpRepository orderItemCmpRepository,
                        ProductService productService, UserService userService, PaymentService paymentService,
                        OptionRepository optionRepository, AuthUtils authUtils, ProductCmpService productCmpService) {
        this.orderRepository = orderRepository;
        this.orderCmpRepository = orderCmpRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderItemCmpRepository = orderItemCmpRepository;
        this.productService = productService;
        this.userService = userService;
        this.paymentService = paymentService;
        this.optionRepository = optionRepository;
        this.authUtils = authUtils;
        this.productCmpService = productCmpService;
    }

    public Order findOrderOrThrowBadRequestException(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));
    }

    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        Order order = findOrderOrThrowBadRequestException(orderId);
        return order.getOrderItems();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public List<OrderCmp> getAllOrdersCmp() {
        return orderCmpRepository.findAll();
    }

    @Transactional
    public List<Order> getUserOrders(String token) {
        // Identifica se usuário indicado existe.
        Long userId = authUtils.getUserId(token);
        UserEntity user = userService.findUserOrThrowNotFoundException(userId);
        // Retorna pedidos do usuário
        return user.getOrders();
    }

    @Transactional
    public List<OrderCmp> getUserCmpOrders(String token) {
        // Identifica se usuário indicado existe.
        Long userId = authUtils.getUserId(token);
        UserEntity user = userService.findUserOrThrowNotFoundException(userId);
        // Retorna pedidos do usuário
        return user.getOrderCmps();
    }

    /**
     * Faz pedido dos produtos (cmp ou regular) e retorna qrcode pix.
     *
     * @param token        token de id do user.
     * @param orderRequest Pedido do cliente.
     * @return qrcode pix.
     */
    public String makeOrder(String token, OrderRequest orderRequest) {
        OrderAndTxId orderProdTxid = new OrderAndTxId();
        OrderAndTxId orderCmpTxid = new OrderAndTxId();
        try{
            this.validateOrder(orderRequest);
        } catch(BadRequestException ex) {
            throw new BadRequestException("Erro: " + ex);
        }
        // Identifica se usuário existe pelo token.
        UserEntity user = userService.findUserOrThrowNotFoundException(authUtils.getUserId(token));
        // Declara var para total dos pedidos cmp e produto.
        double orderTotal = 0;

        //
        if (orderRequest.getRequestProduct() != null && !orderRequest.getRequestProduct().isEmpty()) {
            orderProdTxid = this.totalProducts(user, orderRequest.getRequestProduct());
            orderTotal += orderProdTxid.getTotalValue();
        }

        if (orderRequest.getRequestCmp() != null && !orderRequest.getRequestCmp().isEmpty()) {
            orderCmpTxid = this.totalCmps(user, orderRequest.getRequestCmp());
            orderTotal += orderCmpTxid.getTotalValue();
        }

        // Adicionando valor do frete ao pedido.
        orderTotal += orderRequest.getShipmentFee();

        // Retorna qrCode Pix em base64.
        PixAndTxId pixAndTxId = getPixQrCode(user, orderTotal);

        if (orderProdTxid.getOrderId() != null) {
            Order order = findOrderOrThrowBadRequestException(orderProdTxid.getOrderId());
            order.setTxid(pixAndTxId.getTxId());
            // set enereço ewntrega.
            order.setDeliveryAddress(orderRequest.getDeliveryAddress());
            orderRepository.save(order);
        }
        if (orderCmpTxid.getOrderId() != null) {
            OrderCmp orderCmp = orderCmpRepository.findById(orderCmpTxid.getOrderId()).orElseThrow();
            orderCmp.setTxid(pixAndTxId.getTxId());
            // Seta endereço de entrega.
            orderCmp.setDeliveryAddress(orderRequest.getDeliveryAddress());
            orderCmpRepository.save(orderCmp);
        }

        return pixAndTxId.getBase64();
    }

    public OrderAndTxId totalCmps(UserEntity user, List<RequestCmp> requestCmps) {
        // Salva dados do cmp antes de fazer pedido.
        List<ProductCmp> persistedCmps = new ArrayList<>();
        for (RequestCmp req : requestCmps) {
            req.setProductCmp(ProductCmpMapper.INSTANCE.cmpToDto(
                    productCmpService.createProductCmp(req.getProductCmp())));
        };
        // Itens do pedido cmp para relação com order.
        List<OrderItemCmp> orderItemList = new ArrayList<>();
        double totalValue = 0;
        // Identfica produtos cmp e persiste todos como produtos do pedido do usuário.
        for (RequestCmp reqCmp : requestCmps) {
            // Identifica cmp no BD.
            ProductCmp dbCmp = productCmpService.findProductCmpOrThrowNotFoundException(reqCmp.getProductCmp().getId());
            //Cria item do pedido (identificação do cmp, opções e qtde selecionada).
            OrderItemCmp orderItem = new OrderItemCmp();
            orderItem.getProductCmps().add(dbCmp);
            orderItem.setSelectedAmountOfCmps(reqCmp.getAmount());
            // Calcula valor das seleções das opções.
            double optionsSubtotal = this.calculateOptionsCmpSubtotal(dbCmp);
            // Define o subtotal da compra do "item" (valor das opções do cmp * qtde).
            double subtotal = (optionsSubtotal) * reqCmp.getAmount();
            orderItem.setSubtotal(subtotal);
            // Adiciona produto na relação orderItem.
            orderItemList.add(orderItem);
            // Persiste orderItem.
            orderItemCmpRepository.save(orderItem);
            // Faz relacionamento entre produto selecionado e pedido -> cmp-orderItem.
            dbCmp.getOrderCmps().add(orderItem);
            //persiste mudanças no produto.
            // Soma ao valor total da compra do usuário.
            totalValue += subtotal;
        }
        // Adiciona todos cmps com respectivos subtotais (orderItems) na tabela de pedidos.
        OrderCmp newOrder = new OrderCmp();
        newOrder.getOrderCmpItems().addAll(orderItemList);
        newOrder.setTotalPrice(totalValue);
        // Faz set do momento da compra para 'agora'.
        newOrder.setDate(LocalDateTime.now());
        // Setando usuário que realizou a compra.
        newOrder.setUser(user);
        orderCmpRepository.save(newOrder);
        user.getOrderCmps().add(newOrder);
        userService.saveUser(user);
        //
        OrderAndTxId completeOrder = new OrderAndTxId();
        completeOrder.setOrderId(newOrder.getOrderCmpId());
        completeOrder.setTotalValue(totalValue);
        return completeOrder;
    }

    /**
     * Cria pedido de um cliente identificando itens selecionados e quantidades. Determina subtotal de cada item e
     * persiste os itens do pedido e o pedido completo (relação de orderItems contido em order).
     * Retorna total da compra.
     *
     * @param user            Identificação do usuário.
     * @param requestProducts Dto com os produtos selecionados para compra.
     * @return O total da compra.
     */
    @Transactional
    public OrderAndTxId totalProducts(UserEntity user, List<RequestProduct> requestProducts) {
        // Itens do pedido para relação com order.
        List<OrderItem> orderItemList = new ArrayList<>();
        double totalValue = 0;
        // Identfica produtos do carrinho e persiste todos como produtos do pedido do usuário.
        for (RequestProduct reqProduct : requestProducts) {
            // Identifica produto selecionado no BD.
            Product dbProduct = productService.findProductOrThrowNotFoundException(reqProduct.getProduct().getProductId());
            // Identifica se a quantidade de produtos em estoque é suficiente para a compra.
            if (dbProduct.getQuantity() < reqProduct.getAmount()) {
                throw new ConflictException("Quantidade insuficiente de produtos em estoque para realizar a operação: \nProduto: "
                        + dbProduct.getName() + " Qtde em estoque: " + dbProduct.getQuantity()
                        + " Qtde requisitada na compra: " + reqProduct.getAmount());
            }
            //Cria item do pedido (identificação do produto, opções e qtde selecionada).
            OrderItem orderItem = new OrderItem();
            orderItem.getProducts().add(dbProduct);
            orderItem.setSelectedAmountOfProducts(reqProduct.getAmount());
            // Subtrai quantidade de produtos adquiridos pelo cliente do estoque.
            dbProduct.setQuantity(dbProduct.getQuantity() - reqProduct.getAmount());
            // Se qtde de produto é zero, produto se torna indisponível.
            if (dbProduct.getQuantity() == 0) {
                dbProduct.setAvailable(false);
            }
            // Calcula valor das seleções das opções.
            double optionsSubtotal = this.calculateOptionsSubtotal(dbProduct);
            // Define o subtotal da compra do "item" (valor do produto + opções * qtde).
            double subtotal = (dbProduct.getValue() + optionsSubtotal) * reqProduct.getAmount();
            orderItem.setSubtotal(subtotal);
            // Adiciona produto na relação orderItem.
            orderItemList.add(orderItem);
            // Persiste orderItem.
            orderItemRepository.save(orderItem);
            // Faz relacionamento entre produto selecionado e pedido -> product-orderItem.
            dbProduct.getOrderItems().add(orderItem);
            //persiste mudanças no produto.
            productService.saveProduct(dbProduct);
            // Soma ao valor total da compra do usuário.
            totalValue += subtotal;
        }
        // Adiciona todos Produtos com respectivos subtotais (orderItems) na tabela de pedidos.
        Order newOrder = new Order();
        newOrder.getOrderItems().addAll(orderItemList);
        newOrder.setTotalPrice(totalValue);
        // Faz set do momento da compra para 'agora'.
        newOrder.setDate(LocalDateTime.now());
        // Setando usuário que realizou a compra.
        newOrder.setUser(user);
        orderRepository.save(newOrder);
        user.getOrders().add(newOrder);
        userService.saveUser(user);
        //
        OrderAndTxId completeOrder = new OrderAndTxId();
        completeOrder.setOrderId(newOrder.getOrderId());
        completeOrder.setTotalValue(totalValue);
        return completeOrder;
    }

    @Transactional
    public PixAndTxId getPixQrCode(UserEntity user, Double total) {
        // Envia Requisição para adquirir pix, retorna qrCode pix do valor total do pedido (cmp e produto).
        return paymentService.paymentsPix(user, total);
    }

    private double calculateOptionsSubtotal(Product product) {
        double subtotal = 0;
        if (product.getSections() != null && !product.getSections().isEmpty()) {
            for (Section section : product.getSections()) {
                if (section.getOptions() != null && !section.getOptions().isEmpty()) {
                    for (Option option : section.getOptions()) {
                        subtotal += optionRepository.findById(option.getOptionId()).get().getPrice();
                    }
                }
            }
        }
        return subtotal;
    }

    private double calculateOptionsCmpSubtotal(ProductCmp productCmp) {
        double totalOptionPrice = 0;
        for (SectionCmp sectionCmp : productCmp.getSectionCmps()) {
            for (ElementCmp elementCmp : sectionCmp.getElementCmps()) {
                for (OptionCmp optionCmp : elementCmp.getOptionCmps()) {
                    totalOptionPrice += optionCmp.getPrice();
                }
            }
        }
        return totalOptionPrice;
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = this.findOrderOrThrowBadRequestException(orderId);
        orderRepository.delete(order);
    }

    public void validateOrder(OrderRequest orderRequest) throws BadRequestException {
        // Se ambos pedidos (cmp e prod) são vazios, joga exceção.
        if ((orderRequest.getRequestProduct() == null || orderRequest.getRequestProduct().isEmpty()) &&
                (orderRequest.getRequestCmp() == null || orderRequest.getRequestCmp().isEmpty())
        ) {
            throw new BadRequestException("Erro - Não há produtos na requisição");
        }
    }
}
