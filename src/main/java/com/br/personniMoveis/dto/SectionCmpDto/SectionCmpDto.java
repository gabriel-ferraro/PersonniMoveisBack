package com.br.personniMoveis.dto.SectionCmpDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SectionCmpDto {

    private Long sectionCmpId;

    private String name;

    private String imgUrl;

    private Long categoryId;
}
