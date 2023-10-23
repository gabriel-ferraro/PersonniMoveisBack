package com.br.personniMoveis.model.user;

import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.productCmp.ProductCmp;
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
@Table(name = "order_item_cmp")
public class OrderItemCmp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_item_cmp_id")
    private Long orderItemCmpId;

    /**
     * Unidades do produtos que o cliente selecionou para comprar.
     */
    @Column(name = "selected_amount_of_cmps")
    private Long selectedAmountOfCmps;

    private Double subtotal;

    /**
     * Ordem de pedido dos cmps.
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "orderCmps")
    private final List<ProductCmp> productCmps = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderCmp orderCmp;

    @Override
    public int hashCode() {
        return Objects.hash(orderItemCmpId);
    }
}
