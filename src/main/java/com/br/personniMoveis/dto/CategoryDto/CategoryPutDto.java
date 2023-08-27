package com.br.personniMoveis.dto.CategoryDto;

import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CategoryPutDto {

    @NotNull
    private String name;

    private Set<SectionCmpDto> sectionCmpDtos;

}
