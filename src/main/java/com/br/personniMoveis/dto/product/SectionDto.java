package com.br.personniMoveis.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SectionDto {

    private Long sectionId;
    private String name;
    private String imgUrl;
    private Set<OptionDto> optionPostDtoList;
}
