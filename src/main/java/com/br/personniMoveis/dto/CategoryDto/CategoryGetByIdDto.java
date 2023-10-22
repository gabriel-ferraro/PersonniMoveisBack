package com.br.personniMoveis.dto.CategoryDto;

import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpDto;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryGetByIdDto {

    private Long id;

    private String name;

    private Boolean allow_creation; // Permitir criacao do produto que se encaixa na categoria.

    private Set<SectionCmp> sectionCmps;
}
