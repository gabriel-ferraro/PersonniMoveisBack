package com.br.personniMoveis.dto.SectionCmpDto;

import com.br.personniMoveis.model.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SectionCmpPutDto {

    private Long sectionCmpId;

    private String name;

    private String imgUrl;

    private Category category;

}
