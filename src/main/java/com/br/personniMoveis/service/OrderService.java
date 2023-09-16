package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.OrderRequest;
import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.user.Order;
import com.br.personniMoveis.model.user.OrderItem;
import com.br.personniMoveis.model.user.UserEntity;
import com.br.personniMoveis.repository.OrderItemRepository;
import com.br.personniMoveis.repository.OrderRepository;
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

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        ProductService productService, UserService userService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
        this.userService = userService;
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

    public List<Order> getUserOrders(Long userId) {
        // Identifica se usuário indicado existe.
        userService.findUserOrThrowNotFoundException(userId);
        // Retorna pedidos do usuário via query.
        return orderRepository.getUserOrders(userId);
    }

    /**
     * Cria pedido de um cliente identificando itens selecionados e quantidades. Determina subtotal de cada item e
     * persiste os itens do pedido e o pedido completo (relação de orderItems contido em order).
     * @param userId id do usuário que esta realizando a compra.
     * @param orderRequest Dto com id do cliente e IDs dos produtos selecionados para compra.
     * @return O pedido do cliente persistido.
     */
    @Transactional
    public Order createOrder(Long userId, List<OrderRequest> orderRequest) {
        // Identifica se usuário existe.
        UserEntity user = userService.findUserOrThrowNotFoundException(userId);
        // Itens do pedido.
        List<OrderItem> orderItemList = new ArrayList<>();
        double totalValue = 0D;
        // Identfica produtos do carrinho e persiste todos como produtos do pedido do usuário.
        for (OrderRequest orderProduct : orderRequest) {
            Product newProduct = productService.findProductOrThrowNotFoundException(orderProduct.getProductId());
            //Cria item do pedido (identificação do produto e qtde selecionada).
            OrderItem orderItem = new OrderItem();
            orderItem.getProducts().add(newProduct);
            orderItem.setQuantity(orderProduct.getQuantity());
            // Define o subtotal da compra do "item" (valor do produto * qtde).
            double subtotal = newProduct.getValue() * orderProduct.getQuantity();
            orderItem.setSubtotal(subtotal);
            // Adiciona produto na relação orderItem.
            orderItemList.add(orderItem);
            // Persiste orderItem.
            orderItemRepository.save(orderItem);
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
        return newOrder;
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = this.findOrderOrThrowBadRequestException(orderId);
        orderRepository.delete(order);
    }
}
