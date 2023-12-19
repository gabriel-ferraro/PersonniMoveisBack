package com.br.personniMoveis.dto.SectionCmpDto;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpDto;
import com.br.personniMoveis.model.Category;
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

    private Integer index;

    private Category category;

    @JsonProperty("elementCmps")
    private Set<ElementCmpDto> elementCmpDtos;

    public SectionCmpDto() {

    }
}
