package com.br.personniMoveis.dto.OptionCmpDto;

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

    public OptionCmpDto() {

    }
}
