package com.br.personniMoveis.dto.SectionCmpDto;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpPostDto;
import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpPutDto;
import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class SectionCmpPostDto {

    private Long sectionCmpId;

    private String name;

    private String imgUrl;

    private Long categoryId;

    private final Set<ElementCmpPutDto> elementCmpPutDtos = new HashSet<>();



}
