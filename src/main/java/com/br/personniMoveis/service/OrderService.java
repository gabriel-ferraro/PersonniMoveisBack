package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.OrderRequest;
import com.br.personniMoveis.dto.RequestCmp;
import com.br.personniMoveis.dto.RequestProduct;
import com.br.personniMoveis.exception.InsufficientStockException;
import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.model.product.Option;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.product.Section;
import com.br.personniMoveis.model.user.Order;
import com.br.personniMoveis.model.user.OrderItem;
import com.br.personniMoveis.model.user.UserEntity;
import com.br.personniMoveis.repository.OptionRepository;
import com.br.personniMoveis.repository.OrderItemRepository;
import com.br.personniMoveis.repository.OrderRepository;
import com.br.personniMoveis.service.payment.PaymentService;
import com.br.personniMoveis.service.product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final UserService userService;
    private final TokenService tokenService;
    private final PaymentService paymentService;
    private final OptionRepository optionRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        ProductService productService, UserService userService, TokenService tokenService,
                        PaymentService paymentService, OptionRepository optionRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
        this.userService = userService;
        this.tokenService = tokenService;
        this.paymentService = paymentService;
        this.optionRepository = optionRepository;
    }

    public Order findOrderOrThrowBadRequestException(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));
    }

    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        Order order = findOrderOrThrowBadRequestException(orderId);
        return order.getOrderItems();
    }

    @Transactional
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public List<Order> getUserOrders(String token) {
        // Identifica se usuário indicado existe.
        Long userId = Long.valueOf(tokenService.getClaimFromToken(token, "userId"));
        UserEntity user = userService.findUserOrThrowNotFoundException(userId);
        // Retorna pedidos do usuário
        return user.getOrders();
    }

    public List<Order> getUserCmpOrders() {
        return null;
    }

    /**
     * Faz pedido dos produtos (cmp ou regular) e retorna qrcode pix.
     *
     * @param token        token de id do user.
     * @param orderRequest Pedido do cliente.
     * @return qrcode pix.
     */
    public String makeOrder(String token, OrderRequest orderRequest) {
        // Identifica se usuário existe pelo token.
        Long userId = Long.valueOf(tokenService.getClaimFromToken(token, "userId"));
        UserEntity user = userService.findUserOrThrowNotFoundException(userId);

        // Declara var para total dos pedidos cmp e produto.
        double orderTotal = 0;
        if (orderRequest.getRequestProduct() != null && !orderRequest.getRequestProduct().isEmpty()) {
            orderTotal += this.totalProducts(user, orderRequest.getRequestProduct());
        }
        if (orderRequest.getRequestCmp() != null && !orderRequest.getRequestCmp().isEmpty()) {
            orderTotal += this.totalCmps(user, orderRequest.getRequestCmp());
        }
        // Retorna qrCode Pix em base64.
        return getPixQrCode(user, orderTotal);
    }

    public double totalCmps(UserEntity user, List<RequestCmp> requestCmps) {
        return 0;
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
    public Double totalProducts(UserEntity user, List<RequestProduct> requestProducts) {
        // Itens do pedido para relação com order.
        List<OrderItem> orderItemList = new ArrayList<>();
        double totalValue = 0;
        // Identfica produtos do carrinho e persiste todos como produtos do pedido do usuário.
        for (RequestProduct reqProduct : requestProducts) {
            // Identifica produto selecionado no BD.
            Product dbProduct = productService.findProductOrThrowNotFoundException(reqProduct.getProduct().getProductId());
            // Identifica se a quantidade de produtos em estoque é suficiente para a compra.
            if (dbProduct.getQuantity() < reqProduct.getAmount()) {
                throw new InsufficientStockException("Quantidade insuficiente de produtos em estoque para realizar a operação: \nProduto: "
                        + dbProduct.getName() + " Qtde em estoque: " + dbProduct.getQuantity()
                        + " Qtde requisitada na compra: " + reqProduct.getAmount());
            }
            //Cria item do pedido (identificação do produto, opções e qtde selecionada).
            OrderItem orderItem = new OrderItem();
            orderItem.getProducts().add(dbProduct);
            orderItem.setSelectedAmountOfProducts(reqProduct.getAmount());
            // Subtrai quantidade de produtos adquiridos pelo cliente do estoque.
            dbProduct.setQuantity(dbProduct.getQuantity() - reqProduct.getAmount());
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
        return totalValue;
    }

    @Transactional
    public String getPixQrCode(UserEntity user, Double total) {
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

    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = this.findOrderOrThrowBadRequestException(orderId);
        orderRepository.delete(order);
    }
}
