package com.br.personniMoveis.model;

import com.br.personniMoveis.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_img")
public class ProductImg {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_img_id")
    private Long productImgId;

    @Column(name = "product_image_id")
    private Long productImageId;

    @Column(name = "img_url")
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
