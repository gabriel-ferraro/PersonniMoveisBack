package com.br.personniMoveis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductPutDto {

    private String name;
    private Double value;
    private Long quantity;
    private String description;
    private Boolean editable;
}
