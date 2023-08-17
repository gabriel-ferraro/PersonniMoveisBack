package com.br.personniMoveis.dto.ElementsDto;

import com.br.personniMoveis.model.productCmp.SectionCmp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElementCmpPostDto {
    private Long elementCmpId;

    private String name;

    private String imgUrl;

    private SectionCmp sectionCmp;
}
