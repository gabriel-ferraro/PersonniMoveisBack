package com.br.personniMoveis.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductGetDto {

    private Long productId;
    private String name;
    private Double value;
    private Long quantity;
    private Boolean editable;
    private String imgUrl;
    private String description;
}
