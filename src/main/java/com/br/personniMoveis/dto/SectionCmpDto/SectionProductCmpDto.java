package com.br.personniMoveis.dto.SectionCmpDto;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpDto;
import com.br.personniMoveis.dto.ElementCmpDto.ElementProductCmpDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SectionProductCmpDto {

    private Long sectionId;

    private String name;

    private String imgUrl;

    private Long categoryId;

    private Set<ElementProductCmpDto> elementProductCmpDtos;
}
