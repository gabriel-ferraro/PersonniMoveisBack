package com.br.personniMoveis.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryDto {

    private Long categoryId;
    private String name;
    private Boolean allow_creation;
    private Set<ProductDto> productList;
}
