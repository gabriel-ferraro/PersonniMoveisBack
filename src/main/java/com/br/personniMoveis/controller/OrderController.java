package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.OrderRequest;
import com.br.personniMoveis.model.user.Order;
import com.br.personniMoveis.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controladora para gerenciar pedidos realizados via carrinho do cliente.
 */
@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Recebe uma coleção de itens do pedido (ids dos produtos e a quantidade selecionada de cada), retorna o pedido
     * completo persistido.
     *
     * @param userId id do usuário que esta realizando a compra.
     * @param orderRequest Dto de produtos selecionados pelo usuário para compra.
     * @return Retorna o pedido realizado no carrinho persistido.
     */
    @PostMapping(path = "/{userId}")
    public ResponseEntity<Order> createOrder(@PathVariable("userId") Long userId, @RequestBody @Valid List<OrderRequest> orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(userId, orderRequest));
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping(path = "/{clientId}")
    public ResponseEntity<List<Order>> getAllOrdersFromClientById(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(orderService.getUserOrders(clientId));
    }
}
