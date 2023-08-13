package com.br.personniMoveis.model.productCmp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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
    @Column(name = "product_id")
    private Long productId;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private Long quantity;

    @Column(name = "img_url")
    private String imgUrl;

    private String description;


}
