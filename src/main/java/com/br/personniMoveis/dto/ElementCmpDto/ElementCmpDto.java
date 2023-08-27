package com.br.personniMoveis.dto.ElementCmpDto;

import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpPostDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ElementCmpDto {

    private Long elementCmpId;

    private String name;

    private String imgUrl;

    private Long sectionCmp;

    private Set<OptionCmpDto> optionCmpDtos;
}
