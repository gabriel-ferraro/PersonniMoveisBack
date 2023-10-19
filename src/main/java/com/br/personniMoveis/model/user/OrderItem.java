package com.br.personniMoveis.model.user;

import com.br.personniMoveis.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_item_id")
    private Long orderItemId;

    /**
     * Unidades do produtos que o cliente selecionou para comprar.
     */
    @Column(name = "selected_amount_of_products")
    private Long selectedAmountOfProducts;

    private Double subtotal;

    /**
     * Ordem de pedido dos produtos.
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "orderItems")
    private final List<Product> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Override
    public int hashCode() {
        return Objects.hash(orderItemId);
    }

}
