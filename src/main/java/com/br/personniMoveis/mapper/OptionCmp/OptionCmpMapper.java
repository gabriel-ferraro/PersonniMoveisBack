package com.br.personniMoveis.mapper.OptionCmp;

import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpGetDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpPostDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpPutDto;
import com.br.personniMoveis.model.productCmp.OptionCmp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class OptionCmpMapper {

    public static final OptionCmpMapper INSTANCE = Mappers.getMapper(OptionCmpMapper.class);

    @Mapping(target = "id", ignore = true)
    public abstract OptionCmp toOptionCmp(OptionCmpPostDto optionCmpPostDto);

    @Mapping(target = "id", ignore = true)
    public abstract OptionCmp toOptionCmp(OptionCmpPutDto optionCmpPutDto);

    @Mapping(target = "id", ignore = true)
    public abstract Set<OptionCmp> toOptionCmp(Set<OptionCmpDto> optionCmp);

    public abstract OptionCmpGetDto OptionCmpToOptionCmpGetDto(OptionCmp optionCmp);

}




