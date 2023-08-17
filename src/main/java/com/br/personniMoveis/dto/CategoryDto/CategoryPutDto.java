package com.br.personniMoveis.dto.CategoryDto;

import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpPostDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryPutDto {

    private String name;

    private List<SectionCmpPostDto> sectionCmpPostDtos;

    // Construtor adicional para lidar com o caso da lista ser nula
    public CategoryPutDto(String name) {
        this.name = name;
    }
}
