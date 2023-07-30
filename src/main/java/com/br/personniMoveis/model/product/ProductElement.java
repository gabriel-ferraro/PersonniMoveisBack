package com.br.personniMoveis.model.product;

import com.br.personniMoveis.model.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mapeamento ORM para elementos do produto (elementos que compõem o produto
 * editável).
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_element")
public class ProductElement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private Double value;

    /**
     * Imagem referente à parte editável do produto.
     */
    @Column(name = "element_image_path")
    private String elementImagePath;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productElement;
}
