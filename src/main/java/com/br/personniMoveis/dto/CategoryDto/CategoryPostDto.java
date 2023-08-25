package com.br.personniMoveis.dto.CategoryDto;

import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpPostDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryPostDto {

    private Long categoryId;
    @NotNull
    private String name;
    private Set<SectionCmpPostDto> sectionCmpPostDtos;
}
