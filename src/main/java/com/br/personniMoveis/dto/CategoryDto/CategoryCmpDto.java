package com.br.personniMoveis.dto.CategoryDto;

import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCmpDto {

    private Long id;

    private String name;

    private Boolean allow_creation; // Permitir criação do produto que se encaixa na categoria.

    @JsonProperty("sectionCmps")
    private Set<SectionCmpDto> sectionCmpDtos;
}
