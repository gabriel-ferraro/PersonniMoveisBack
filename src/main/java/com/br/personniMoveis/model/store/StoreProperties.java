package com.br.personniMoveis.model.store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mapeamento ORM para propriedades da loja do e-commerce.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "store_properties")
public class StoreProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long storeId;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_logo_path")
    private String storeLogoPath;

    @Column(name = "store_email")
    private String storeEmail;
    
    @Column(name = "about_us_info")
    private String aboutUsInfo;
    
    @Column(name = "store_address")
    private String storeAddress;
    
    @Column(name = "store_phone")
    private String storePhone;

    @Column(name = "primary_collor")
    private String primaryCollor;

    @Column(name = "secondary_collor")
    private String secondaryCollor;

    /**
     * Controle para definir se pedidos CMP devem ser avaliados ou não antes do
     * cliente poder realizar a compra.
     */
    @Column(name = "is_client_order_evaluatd")
    private Boolean isClientOrderEvaluated;
}
