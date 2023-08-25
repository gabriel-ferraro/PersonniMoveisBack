package com.br.personniMoveis.dto.product.get;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionProductGetDto {

    private Long optionId;
    private String name;
    private String imgUrl;
    private Double price;
}
