package com.br.personniMoveis.dto.ElementCmpDto;

import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionProductCmpDto;
import com.br.personniMoveis.model.productCmp.OptionCmp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ElementProductCmpDto {

    private Long id;

    private String name;

    private String imgUrl;

    private Long sectionCmpId;

    private OptionProductCmpDto optionProductCmpDto;

}
