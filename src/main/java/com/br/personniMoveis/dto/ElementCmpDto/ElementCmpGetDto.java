package com.br.personniMoveis.dto.ElementCmpDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElementCmpGetDto {

    private Long id;

    private String name;

    private String imgUrl;

    private String type;

    private Long sectionCmpId;
}
