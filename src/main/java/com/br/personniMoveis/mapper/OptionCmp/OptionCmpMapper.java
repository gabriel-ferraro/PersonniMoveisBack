package com.br.personniMoveis.mapper.OptionCmp;

import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpGetDto;
import com.br.personniMoveis.model.productCmp.OptionCmp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class OptionCmpMapper {

    public static final OptionCmpMapper INSTANCE = Mappers.getMapper(OptionCmpMapper.class);

    @Mapping(target = "id", ignore = true)
    public abstract Set<OptionCmp> toOptionCmpList(Set<OptionCmpDto> optionCmp);
    @Mapping(target = "id", ignore = true)
    public abstract OptionCmp toOptionCmp(OptionCmpDto optionCmpDto);

    public abstract OptionCmpGetDto OptionCmpToOptionCmpGetDto(OptionCmp optionCmp);

}




