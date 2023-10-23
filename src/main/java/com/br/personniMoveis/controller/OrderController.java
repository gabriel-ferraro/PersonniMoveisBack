package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.OrderRequest;
import com.br.personniMoveis.model.user.Order;
import com.br.personniMoveis.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controladora para gerenciar pedidos realizados via carrinho do cliente.
 */
@RestController
@RequestMapping("orders")
@SecurityRequirement(name = "bearer-key")
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
     * @param token         tem id do usuário que esta realizando a compra.
     * @param orderRequest Dto de produtos selecionados pelo usuário para compra.
     * @return Retorna o pedido realizado no carrinho persistido.
     */
    @PostMapping(path = "/create-order")
    public ResponseEntity<String> createProductOrder(
            @RequestHeader("Authorization") String token, @RequestBody @Valid OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.makeOrder(token, orderRequest));
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping(path = "client-orders")
    public ResponseEntity<List<Order>> getAllOrdersFromClientById(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(orderService.getUserOrders(token));
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteOrder(Long orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
