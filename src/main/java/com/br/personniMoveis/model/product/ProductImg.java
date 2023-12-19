package com.br.personniMoveis.model.product;

import com.br.personniMoveis.model.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductImg {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_img_id")
    private Long productImgId;

    private String img;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

}
