package com.br.personniMoveis.dto.ElementCmpDto;

import com.br.personniMoveis.dto.OptionCmpDto.OptionProductCmpDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElementProductCmpDto {

    private Long id;

    private String name;

    private String imgUrl;

    private String type;

    private Integer index;

    private Long sectionCmpId;

    private Boolean mandatory;

    private OptionProductCmpDto optionProductCmpDto;

}
