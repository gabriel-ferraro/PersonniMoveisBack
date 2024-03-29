package com.br.personniMoveis.dto.SectionCmpDto;

import com.br.personniMoveis.dto.ElementCmpDto.ElementProductCmpDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionProductCmpDto {

    private Long sectionId;

    private String name;

    private String imgUrl;

    private Long categoryId;

    private Integer index;

    private Set<ElementProductCmpDto> elementProductCmpDtos;
}
