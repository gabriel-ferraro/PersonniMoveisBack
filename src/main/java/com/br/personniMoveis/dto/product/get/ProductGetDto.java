package com.br.personniMoveis.dto.product.get;

import com.br.personniMoveis.dto.product.ProductDto;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Dto contendo somente os dados base do produto (sem relacionamentos).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductGetDto extends ProductDto {

    public ProductGetDto(Long productId, String name, Double value, Long quantity,
                         Boolean editable, String mainImgUrl, String description) {
        super(productId, name, value, quantity, editable, mainImgUrl, description,
                null, null, null, null, null);
    }
}
