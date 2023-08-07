package com.br.personniMoveis.dto;

import lombok.Data;

@Data
public class ProductPutDto {

    private String name;
    private Double value;
    private Long quantity;
    private String description;
    private Boolean editable;
}
