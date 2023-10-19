package com.br.personniMoveis.dto.OptionCmpDto;

import com.br.personniMoveis.model.productCmp.ElementCmp;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionCmpDto {

    private Long id;

    private String name;

    private Double price;

    private String img;

    private ElementCmp elementCmp;

    public OptionCmpDto() {

    }
}
