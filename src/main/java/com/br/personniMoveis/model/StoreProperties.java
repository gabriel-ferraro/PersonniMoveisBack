package com.br.personniMoveis.model;

import jakarta.persistence.*;
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

    @Column(name = "address_meta")
    private String addressMeta;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_logo_path")
    private String storeLogoPath;

    @Column(name = "store_logo_secondary_path")
    private String storeSecondaryImgPath;

    @Column(name = "store_placeholder_path")
    private String storePlaceholdeImgPath;

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

    @Column(name = "site_context")
    private String siteContext;

    public StoreProperties(StoreProperties sp) {
        this.storeId = sp.getStoreId();
        this.storeName = sp.getStoreName();
        this.storeLogoPath = sp.getStoreLogoPath();
        this.storeSecondaryImgPath = sp.getStoreSecondaryImgPath();
        this.storePlaceholdeImgPath = sp.getStorePlaceholdeImgPath();
        this.storeEmail = sp.getStoreEmail();
        this.aboutUsInfo = sp.getAboutUsInfo();
        this.storeAddress = sp.getStoreAddress();
        this.storePhone = sp.getStorePhone();
        this.primaryCollor = sp.getPrimaryCollor();
        this.secondaryCollor = sp.getSecondaryCollor();
        this.addressMeta = sp.getAddressMeta();
        this.siteContext =  sp.getSiteContext();
    }
}
