package com.br.personniMoveis.dto.ElementCmpDto;

import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpPutDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class ElementCmpPutDto {

    private Long id;

    private String name;

    private String imgUrl;

    private Long sectionCmpId;

    private final Set<OptionCmpPutDto> optionCmpPutDtos = new HashSet<>();
}
