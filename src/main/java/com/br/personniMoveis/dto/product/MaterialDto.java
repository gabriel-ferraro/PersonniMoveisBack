package com.br.personniMoveis.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class MaterialDto {

    private Long materialId;
    @NonNull
    private String materialName;
    private String imgUrl;
    @NonNull
    private Double price;
}
