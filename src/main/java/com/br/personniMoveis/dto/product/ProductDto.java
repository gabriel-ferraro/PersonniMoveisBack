package com.br.personniMoveis.dto.product;

import com.br.personniMoveis.model.ProductImg;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProductDto {

    private Long productId;
    private String name;
    private Double value;
    private Long quantity;
    private Boolean editable;
    private String mainImg;
    private String description;
    private Set<ProductImg> secondaryImages;
    private Set<DetailDto> detailList;
    private Set<MaterialDto> materialList;
    private Set<SectionDto> sectionList;
    private Set<TagDto> tagList;
}
