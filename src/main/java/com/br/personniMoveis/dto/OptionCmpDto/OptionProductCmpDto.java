package com.br.personniMoveis.dto.OptionCmpDto;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionProductCmpDto {

    private Long id;

    private String name;

    private Double price;

    private String descriptions;

    private String img;

    private Long elementCmpId;

}
