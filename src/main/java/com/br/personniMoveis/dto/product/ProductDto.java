package com.br.personniMoveis.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProductDto {

    private Long productId;
    @NonNull
    private String name;
    @NonNull
    private Double value;
    @NonNull
    private Long quantity;
    @NonNull
    private Boolean editable;
    private String imgUrl;
    private String description;
    private Set<DetailDto> detailPostList;
    private Set<MaterialDto> materialPostList;
    private Set<TagDto> tagPostList;
    private Set<SectionDto> sectionPostList;
}
