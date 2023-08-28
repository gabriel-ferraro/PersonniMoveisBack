package com.br.personniMoveis.dto.product.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
@AllArgsConstructor
public class SectionDto {

    @NonNull
    private String name;
    private String imgUrl;
    private Set<OptionDto> optionPostDtoList;
}
