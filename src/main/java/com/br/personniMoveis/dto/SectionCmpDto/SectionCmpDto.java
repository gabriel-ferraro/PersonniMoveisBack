package com.br.personniMoveis.dto.SectionCmpDto;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SectionCmpDto {

    private Long id;

    private String name;

    private String imgUrl;

    private Long categoryId;

    @JsonProperty("elementCmps")
    private Set<ElementCmpDto> elementCmpDtos;

    public SectionCmpDto() {

    }
}
