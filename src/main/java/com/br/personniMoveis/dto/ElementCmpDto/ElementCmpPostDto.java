package com.br.personniMoveis.dto.ElementCmpDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElementCmpPostDto {
    private Long id;

    private String name;

    private String imgUrl;

    private Long sectionCmpId;


}
