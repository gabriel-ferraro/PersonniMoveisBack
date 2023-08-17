package com.br.personniMoveis.dto.ProductCmp;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductCmpOptionDto {

    private String productCmpOprtionId;
    private String name;
    private Double price;
    private String imageUrl;
    private List<ProductCmpTypeDto> productCmpTypeDtos;

}
