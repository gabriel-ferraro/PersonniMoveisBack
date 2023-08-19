package com.br.personniMoveis.dto.ElementCmpDto;

import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpPostDto;
import com.br.personniMoveis.model.productCmp.OptionCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class ElementCmpPostDto {
    private Long elementCmpId;

    private String name;

    private String imgUrl;

    private Long sectionCmpId;


}
