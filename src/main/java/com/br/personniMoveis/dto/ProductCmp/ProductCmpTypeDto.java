package com.br.personniMoveis.dto.ProductCmp;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ProductCmpTypeDto {
    private String name;
    private List<ProductCmpOptionDto> productCmpOptionDtoList;
}
