package com.br.personniMoveis.dto.OptionCmpDto;

import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionCmpGetDto {

    private Long optionCmpId;

    private String name;

    private Double price;

    private String imgUrl;

    private ElementCmp elementCmp;
}
