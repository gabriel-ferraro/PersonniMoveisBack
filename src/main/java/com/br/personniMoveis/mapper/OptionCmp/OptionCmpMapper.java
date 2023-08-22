package com.br.personniMoveis.mapper.OptionCmp;

import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpGetDto;
import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpPostDto;
import com.br.personniMoveis.dto.ElementCmpDto.ElementCmpPutDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpGetDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpPostDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionCmpPutDto;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.OptionCmp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class OptionCmpMapper {

    public static final OptionCmpMapper INSTANCE = Mappers.getMapper(OptionCmpMapper.class);

    @Mapping(target = "optionCmpId", ignore = true)
    public abstract OptionCmp toOptionCmp(OptionCmpPostDto optionCmpPostDto);

    @Mapping(target = "optionCmpId", ignore = true)
    public abstract OptionCmp toOptionCmp(OptionCmpPutDto optionCmpPutDto);

    public abstract OptionCmpGetDto OptionCmpToOptionCmpGetDto(OptionCmp optionCmp);

}




