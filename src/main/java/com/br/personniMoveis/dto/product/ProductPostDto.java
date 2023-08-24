package com.br.personniMoveis.dto.product;

import com.br.personniMoveis.dto.CategoryDto.CategoryPostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProductPostDto {

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
    private Set<CategoryPostDto> categoryPostList;
    private Set<DetailPostDto> detailPostList;
    private Set<TagPostDto> tagPostList;
    private Set<SectionPostDto> sectionPostList;
    private Set<MaterialPostDto> materialPostList;
}
