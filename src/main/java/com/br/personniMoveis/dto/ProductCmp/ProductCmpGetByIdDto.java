package com.br.personniMoveis.dto.ProductCmp;


import com.br.personniMoveis.model.productCmp.SectionCmp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProductCmpGetByIdDto {

    private Long id;

    private Double valueTotal;

    private Long quantity;

    private String imgUrl;

    private String description;

    private Set<SectionCmp> sectionCmps;

}