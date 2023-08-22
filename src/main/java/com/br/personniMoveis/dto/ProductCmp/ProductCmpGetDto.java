package com.br.personniMoveis.dto.ProductCmp;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCmpGetDto {

    private Double value;

    private Long quantity;

    private String imgUrl;

    private String description;
}