package com.br.personniMoveis.mapper.SectionCmp;

import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpGetDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpPostDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpPutDto;
import com.br.personniMoveis.dto.product.post.SectionDto;
import com.br.personniMoveis.model.product.Section;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class SectionCmpMapper {

    public static final SectionCmpMapper INSTANCE = Mappers.getMapper(SectionCmpMapper.class);

    @Mapping(target = "sectionCmpId", ignore = true)
    public abstract Set<SectionCmp> toSectionCmpPost(Set<SectionCmpPostDto> sectionCmpPostDto);

    @Mapping(target = "sectionId", ignore = true)
    public abstract Section sectionDtoToSection(SectionDto sectiondto);

    @Mapping(target = "sectionCmpId", ignore = true)
    public abstract Set<SectionCmp> toSectionCmpPut(Set<SectionCmpPutDto> sectionCmpPutDto);

    public abstract SectionCmpGetDto SectionToSectionGetDto(SectionCmp sectionCmp);

}
