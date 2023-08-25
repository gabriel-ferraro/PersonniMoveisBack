package com.br.personniMoveis.dto.product.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryProductPost {

    @NonNull
    private String name;
    @NonNull
    private Boolean allow_creation;
    private Set<ProductPostDto> productList;
}
