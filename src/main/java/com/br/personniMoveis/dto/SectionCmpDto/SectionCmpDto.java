package com.br.personniMoveis.dto.SectionCmpDto;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SectionCmpDto {

    private Long sectionCmpId;

    private String name;

    private String imgUrl;

    private Long categoryId;

    private Set<ElementCmpDto> elementCmpDtos;
}
