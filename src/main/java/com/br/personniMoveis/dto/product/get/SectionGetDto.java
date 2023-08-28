package com.br.personniMoveis.dto.product.get;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SectionGetDto {

    private Long sectionId;
    private String name;
    private String imgUrl;
    private Set<OptionProductGetDto> optionPostDtoList;
}
