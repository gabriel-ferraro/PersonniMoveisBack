package com.br.personniMoveis.dto.OptionCmpDto;

import com.br.personniMoveis.model.productCmp.ElementCmp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionCmpPutDto {

    private Long optionCmpId;

    private String name;

    private Double price;

    private String imgUrl;

    private ElementCmp elementCmp;
}
