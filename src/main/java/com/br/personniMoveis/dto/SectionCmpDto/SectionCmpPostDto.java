package com.br.personniMoveis.dto.SectionCmpDto;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpPutDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class SectionCmpPostDto {

    private Long id;

    @NotNull
    private String name;

    private String imgUrl;


    @NotNull
    private Long categoryId;

    private final Set<ElementCmpPutDto> elementCmpPutDtos = new HashSet<>();



}
