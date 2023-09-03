package com.br.personniMoveis.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProductDto {

    private Long productId;
    private String name;
    private Double value;
    private Long quantity;
    private Boolean editable;
    private String imgUrl;
    private String description;
    private Set<DetailDto> detailPostList;
    private Set<MaterialDto> materialPostList;
    private Set<TagDto> tagPostList;
    private Set<SectionDto> sectionPostList;
}
