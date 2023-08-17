package com.br.personniMoveis.dto.SectionCmpDto;

import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SectionCmpPostDto {

    private Long sectionCmpId;

    private String name;

    private String imgUrl;

    private Category category;
}
