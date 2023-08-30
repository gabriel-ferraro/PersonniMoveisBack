package com.br.personniMoveis.dto.CategoryDto;

import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    private String name;

    private Set<SectionCmpDto> sectionCmpsDtos;
}
