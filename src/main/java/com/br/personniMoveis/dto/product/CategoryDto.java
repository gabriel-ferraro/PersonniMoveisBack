package com.br.personniMoveis.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryDto {

    private Long categoryId;
    @NonNull
    private String name;
    @NonNull
    private Boolean allow_creation;
    private Set<ProductDto> productList;
}
