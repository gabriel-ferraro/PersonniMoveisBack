package com.br.personniMoveis.mapper;

import com.br.personniMoveis.dto.store.StorePropertiesCollorDto;
import com.br.personniMoveis.model.StoreProperties;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapeamento para alterar cores do e-commerce.
 */
@Mapper(componentModel = "spring")
public abstract class StorePropertiesColorsMapper {

    public static final StorePropertiesColorsMapper INSTANCE = Mappers.getMapper(StorePropertiesColorsMapper.class);

    public abstract StoreProperties toStoreProperties(StorePropertiesCollorDto StorePropertiesDto);
}
