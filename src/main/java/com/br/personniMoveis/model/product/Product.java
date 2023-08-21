package com.br.personniMoveis.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Mapeamento ORM para produtp.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id")
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    @Builder.Default
    private Boolean editable = false; // Produto não é editável por padrão.

    @Column(name = "img_url")
    private String imgUrl;

    private String description;

    /**
     * Details são campos descritivos do produto, exemplo: peso do produto - A cadeira X é leve e tem só x kg.
     */
    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private final Set<Detail> details = new HashSet<>();

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private final Set<Material> materialList = new HashSet<>();

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private final Set<Section> sections = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_tag", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private final Set<Tag> tags = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
