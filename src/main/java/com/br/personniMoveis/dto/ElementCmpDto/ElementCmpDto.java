package com.br.personniMoveis.dto.ElementCmpDto;

import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpPostDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ElementCmpDto {

    private Long id;

    private String name;

    private String imgUrl;

    private Long sectionCmpId;

    private Set<OptionCmpDto> optionCmpDtos;

    public ElementCmpDto() {

    }
}
