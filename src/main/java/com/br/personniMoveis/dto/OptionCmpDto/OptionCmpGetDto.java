package com.br.personniMoveis.dto.OptionCmpDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionCmpGetDto {

    private Long id;

    private String name;

    private Double price;

    private String descriptions;

    private String img;

    private Long elementCmpId;
}
