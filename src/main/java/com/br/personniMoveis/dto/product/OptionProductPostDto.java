package com.br.personniMoveis.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionProductPostDto {

    private String name;
    private String imgUrl;
    private Double price;
}
