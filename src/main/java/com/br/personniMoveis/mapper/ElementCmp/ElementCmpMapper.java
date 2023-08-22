package com.br.personniMoveis.mapper.ElementCmp;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpGetDto;
import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpPostDto;
import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpPutDto;
import com.br.personniMoveis.model.productCmp.ElementCmp;
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
