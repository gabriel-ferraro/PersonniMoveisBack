package com.br.personniMoveis.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaterialDto {

    private Long materialId;
    private String materialName;
    private String imgUrl;
    private Double price;
}
