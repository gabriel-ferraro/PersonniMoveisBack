package com.br.personniMoveis.dto.ProductCmp;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductCmpSectionDto {

    private String productCmpSectionId;
    private String name;
    private List<ProductCmpElementDto> productCmpElementDtos;

}
