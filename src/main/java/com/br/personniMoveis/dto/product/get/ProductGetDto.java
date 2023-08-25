package com.br.personniMoveis.dto.product.get;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProductGetDto {

    private Long productId;
    private String name;
    private Double value;
    private Long quantity;
    private Boolean editable;
    private String imgUrl;
    private String description;
    private Set<DetailGetDto> detailPostList;
    private Set<TagGetDto> tagPostList;
    private Set<SectionGetDto> sectionPostList;
    private Set<MaterialGetDto> materialPostList;
}
