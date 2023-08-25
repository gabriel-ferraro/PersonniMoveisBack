package com.br.personniMoveis.dto.product.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaterialPostDto {

    private String materialName;
    private String imgUrl;
    private Double price;
}
