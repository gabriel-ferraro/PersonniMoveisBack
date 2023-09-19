package com.br.personniMoveis.mapper.SectionCmp;

import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpGetDto;
import com.br.personniMoveis.dto.product.SectionDto;
import com.br.personniMoveis.model.product.Section;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class SectionCmpMapper {

    public static final SectionCmpMapper INSTANCE = Mappers.getMapper(SectionCmpMapper.class);

    @Mapping(target = "id", ignore = true)
    public abstract Set<SectionCmp> toSectionCmpList(Set<SectionCmpDto> sectionCmpDtos);

    public abstract SectionCmp toSectionCmp(SectionCmpDto sectionCmpDtos);

    public abstract SectionCmpGetDto SectionToSectionGetDto(SectionCmp sectionCmp);

    public abstract Section sectionDtoToSection(SectionDto sectionDto);

}
