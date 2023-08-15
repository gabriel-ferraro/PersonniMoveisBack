package com.br.personniMoveis.model.product;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_tag", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private final Set<Tag> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_section", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "section_id"))
    private final Set<Section> sections = new HashSet<>();
    
    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    public static void addTag(Product product, Tag tag) {
        // Associa tag à produto.
        product.tags.add(tag);
        tag.getProducts().add(product);
    }
}
