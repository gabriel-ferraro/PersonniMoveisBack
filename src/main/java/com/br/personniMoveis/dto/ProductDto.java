package com.br.personniMoveis.dto;

import lombok.Data;

@Data
public class ProductDto {

    private String name;
    private Double value;
    private Long quantity;
    private Boolean editable;
    private String imgUrl;
    private String description;
}
