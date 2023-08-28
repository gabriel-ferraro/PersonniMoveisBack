package com.br.personniMoveis.dto.OptionCmpDto;

import com.br.personniMoveis.model.productCmp.ElementCmp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionCmpDto {

    private Long id;

    private String name;

    private Double price;

    private String imgUrl;

    private Long elementCmpId;
}
