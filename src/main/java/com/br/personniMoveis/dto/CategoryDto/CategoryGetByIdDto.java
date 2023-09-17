package com.br.personniMoveis.dto.CategoryDto;

import com.br.personniMoveis.model.productCmp.SectionCmp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryGetByIdDto {

    private Long id;

    private String name;

    @Builder.Default
    private Boolean allow_creation = true; // Permitir criação do produto que se encaixa na categoria.

    private Set<SectionCmp> sectionCmps;
}
