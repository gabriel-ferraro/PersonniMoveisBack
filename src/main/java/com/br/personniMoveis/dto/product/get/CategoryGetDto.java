package com.br.personniMoveis.dto.product.get;

import com.br.personniMoveis.dto.product.post.ProductPostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryGetDto {

    private Long productId;
    private String name;
    private Boolean allow_creation;
    private Set<ProductGetDto> productList;
}
