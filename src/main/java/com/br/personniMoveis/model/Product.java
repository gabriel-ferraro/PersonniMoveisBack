package com.br.personniMoveis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
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
/**
 * Classe template temporária para o produto "padrão" e produto "editável".
 */
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
    // Valor default para produto editável é false.
    private Boolean editable = false;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientOrder clientOrder;
}
