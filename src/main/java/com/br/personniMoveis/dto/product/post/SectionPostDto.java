package com.br.personniMoveis.dto.product.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SectionPostDto {

    private String name;
    private String imgUrl;
    private Set<OptionProductPostDto> optionPostDtoList;
}
