package com.br.personniMoveis.dto.ElementCmpDto;

import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ElementCmpDto {

    private Long id;

    private String name;

    private String imgUrl;

    private String type;

    private Long sectionCmpId;

    private Boolean mandatory;

    @JsonProperty("optionCmps")
    private Set<OptionCmpDto> optionCmpDtos;

    public ElementCmpDto() {

    }
}
