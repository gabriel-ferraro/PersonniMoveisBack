package com.br.personniMoveis.dto.product.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryDto {

    private String name;
    private Boolean allow_creation;
    private Set<ProductDto> productList;
}
