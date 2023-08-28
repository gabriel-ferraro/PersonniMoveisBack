package com.br.personniMoveis.dto.product.get;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryDto {

    private Long categoryId;
    private String name;
    private Boolean allow_creation;
    private List<ProductGetDto> productList;
}
