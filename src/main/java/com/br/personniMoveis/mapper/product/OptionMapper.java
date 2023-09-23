package com.br.personniMoveis.mapper.product;

import com.br.personniMoveis.dto.product.OptionDto;
import com.br.personniMoveis.model.product.Option;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapeamento para opção da seção.
 */
@Mapper(componentModel = "spring")
public abstract class OptionMapper {

    public static final OptionMapper INSTANCE = Mappers.getMapper(OptionMapper.class);

    public abstract Option optionDtoToOption(OptionDto option);
}
