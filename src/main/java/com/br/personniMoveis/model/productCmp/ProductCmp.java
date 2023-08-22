package com.br.personniMoveis.model.productCmp;

import com.br.personniMoveis.model.category.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "product_cmp_id")
    private Long productCmpId;

    @Column(nullable = false)
    private Double valueTotal;

    @Column(nullable = false)
    private Long quantity;

    @Column(name = "img_url")
    private String imgUrl;

    private String description;

    @ManyToMany
    @JoinTable(name = "section_product_cmp", joinColumns = @JoinColumn(name = "product_cmp_id"), inverseJoinColumns = @JoinColumn(name = "section_cmp_id"))
    private final Set<SectionCmp> sectionCmps = new HashSet<>();

}
