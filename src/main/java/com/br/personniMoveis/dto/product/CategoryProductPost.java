package com.br.personniMoveis.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryProductPost {

    @NonNull
    private Long categoryId;
    @NonNull
    private String categoryName;
    @NonNull
    private Boolean allow_creation;
    private Set<ProductPostDto> productList;
}
