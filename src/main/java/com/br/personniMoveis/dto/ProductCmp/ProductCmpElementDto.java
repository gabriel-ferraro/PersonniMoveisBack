package com.br.personniMoveis.dto.ProductCmp;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductCmpElementDto {

    private String productCmpElementId;
    private String name;
    private List<ProductCmpOptionDto> productCmpOptionDtos;

}
