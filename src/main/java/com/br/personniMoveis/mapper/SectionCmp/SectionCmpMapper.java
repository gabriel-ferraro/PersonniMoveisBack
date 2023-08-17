package com.br.personniMoveis.mapper.SectionCmp;

import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpPostDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpGetDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpPutDto;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class SectionCmpMapper {

    public static final SectionCmpMapper INSTANCE = Mappers.getMapper(SectionCmpMapper.class);

    @Mapping(target = "sectionCmpId", ignore = true)
    public abstract SectionCmp toSectionCmp(SectionCmpPostDto sectionCmpPostDto);

    @Mapping(target = "sectionCmpId", ignore = true)
    public abstract SectionCmp toSectionCmp(SectionCmpPutDto sectionCmpPutDto);

    public abstract SectionCmpGetDto SectionToSectionGetDto(SectionCmp sectionCmp);

}
