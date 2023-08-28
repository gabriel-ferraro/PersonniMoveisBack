package com.br.personniMoveis.dto.CategoryDto;

import com.br.personniMoveis.model.productCmp.SectionCmp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryGetByIdDto {

    private Long id;

    private String name;

    private Set<SectionCmp> sectionCmps;
}
