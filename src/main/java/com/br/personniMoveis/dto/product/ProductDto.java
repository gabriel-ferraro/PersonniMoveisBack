package com.br.personniMoveis.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {

    private String name;
    private String value;
    private String quantity;
    private String editable;
    private String imgUrl;
    private String description;
}
