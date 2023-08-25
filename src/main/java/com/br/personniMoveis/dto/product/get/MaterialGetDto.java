package com.br.personniMoveis.dto.product.get;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaterialGetDto {

    private Long materialId;
    private String materialName;
    private String imgUrl;
    private Double price;
}
