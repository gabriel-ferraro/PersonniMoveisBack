package com.br.personniMoveis.dto.ProductCmp;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductCmpDto {

    private Double value;

    private Long quantity;

    private String imgUrl;

    private String description;

    private List<ProductCmpSectionDto> productSectionDtoList;
}