package com.br.personniMoveis.dto.ProductCmp;

import com.br.personniMoveis.dto.SectionCmpDto.SectionProductCmpDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCmpDto {

    private Long id;

    private Double value;

    private Long quantity;

    private String imgUrl;

    private String description;

    private Set<SectionProductCmpDto> sectionProductCmpDtos;

}
