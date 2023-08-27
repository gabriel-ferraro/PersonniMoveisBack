package com.br.personniMoveis.dto.SectionCmpDto;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpPutDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class SectionCmpPutDto {

    private Long sectionCmpId;

    private String name;

    private String imgUrl;

    private Long categoryId;

    private final Set<ElementCmpPutDto> elementCmpPutDtos = new HashSet<>();

}
