package com.br.personniMoveis.dto.product.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto contendo somente os dados base do produto (sem relacionamentos).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGetDto {

    private Long productId;
    private String name;
    private Double value;
    private Long quantity;
    private String editable;
    private String imgUrl;
    private String description;
}
