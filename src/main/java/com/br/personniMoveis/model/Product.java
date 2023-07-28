package com.br.personniMoveis.model;

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
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe template temporária para o produto "padrão" e produto "editável".
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
    
    @NotEmpty
    private String name;
    
    @NotEmpty
    private Double value;
    
    @NotEmpty
    private Long quantity;
    
    @NotEmpty
    private String description;
    
    @NotEmpty
    @Builder.Default
    // Produto não é editável por padrão.
    private Boolean editable = false;
    
    @JsonIgnore
    @OneToMany(mappedBy = "productElement")
    @Setter(AccessLevel.NONE)
    private final ArrayList<ProductElement> productElementList = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientOrder clientOrder;
}
