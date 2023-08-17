package com.br.personniMoveis.mapper.ElementCmp;

import com.br.personniMoveis.dto.ElementsDto.ElementCmpGetDto;
import com.br.personniMoveis.dto.ElementsDto.ElementCmpPostDto;
import com.br.personniMoveis.dto.ElementsDto.ElementCmpPutDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpGetDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpPostDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpPutDto;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ElementCmpMapper {

    public static final ElementCmpMapper INSTANCE = Mappers.getMapper(ElementCmpMapper.class);

    @Mapping(target = "elementCmpId", ignore = true)
    public abstract ElementCmp toElementCmp(ElementCmpPostDto elementCmpPostDto);

    @Mapping(target = "elementCmpId", ignore = true)
    public abstract ElementCmp toElementCmp(ElementCmpPutDto elementCmpPutDto);

    public abstract ElementCmpGetDto ElementCmpToElementCmpGetDto(ElementCmp elementCmp);

}
