package com.br.personniMoveis.model.productCmp;

import com.br.personniMoveis.model.user.OrderItemCmp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * Mapeamento ORM para o produto "padrão" e produto "editável".
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_cmp")
public class ProductCmp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_cmp_id")
    private Long id;

    @Column(nullable = false)
    private Double valueTotal;

    @Column(nullable = false)
    private Long quantity;

    @Lob
    @Column(name = "mainImg", columnDefinition = "TEXT")
    private String img;

    private String description;

    @OneToMany
    private Set<SectionCmp> sectionCmps = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "cmp_order_item", joinColumns = @JoinColumn(name = "product_cmp_id"), inverseJoinColumns = @JoinColumn(name = "order_item_cmp_id"))
    private final List<OrderItemCmp> orderCmps = new ArrayList<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
