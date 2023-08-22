package com.br.personniMoveis.dto.CategoryDto;

import com.br.personniMoveis.dto.ProductCmp.ProductCmpOptionDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpPostDto;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryPostDto {

    private Long categoryId;

    @NotNull
    private String name;

    private Set<SectionCmpDto> sectionCmpDtos;

}
