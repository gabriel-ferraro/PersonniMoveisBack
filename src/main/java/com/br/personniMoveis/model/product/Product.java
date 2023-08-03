package com.br.personniMoveis.model.product;

import com.br.personniMoveis.model.user.ClientOrder;
import com.br.personniMoveis.model.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Mapeamento ORM para o produto "padrão" e produto "editável".
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

    private String description;

    @Column(nullable = false)
    @Builder.Default
    private Boolean editable = false; // Produto não é editável por padrão.
    
    @Column(name = "main_image_path")
    private String mainImagePath;
    
    @JsonIgnore
    @OneToMany(mappedBy = "productTag")
    @Setter(AccessLevel.NONE)
    private final HashSet<ProductTag> tagsList = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "productElement")
    @Setter(AccessLevel.NONE)
    private final HashSet<ProductElement> productElementList = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "client_order_id")
    private ClientOrder clientOrder;
    
    @ManyToOne
    @JoinColumn(name = "client_waiting_product_id")
    private UserEntity productWaiting;
}
