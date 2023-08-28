package com.br.personniMoveis.mapper;

import com.br.personniMoveis.dto.product.post.OptionDto;
import com.br.personniMoveis.model.product.Option;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapeamento para opção da seção.
 */
@Mapper(componentModel = "spring")
public abstract class OptionMapper {

    public static final OptionMapper INSTANCE = Mappers.getMapper(OptionMapper.class);

    @Mapping(target = "optionId", ignore = true)
    public abstract Option optionDtoToOption(OptionDto option);
}