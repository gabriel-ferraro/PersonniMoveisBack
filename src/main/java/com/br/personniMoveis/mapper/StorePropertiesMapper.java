package com.br.personniMoveis.mapper;

import com.br.personniMoveis.dto.StorePropertiesDto;
import com.br.personniMoveis.model.store.StoreProperties;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapeamento para alterar configurações do e-commerce na entidade
 * StoreProperties.
 */
@Mapper(componentModel = "spring")
public abstract class StorePropertiesMapper {

    public static final StorePropertiesMapper INSTANCE = Mappers.getMapper(StorePropertiesMapper.class);

    @Mapping(target = "storeId", ignore = true)
    public abstract StoreProperties storePropertiesDtoToStoreProperties(StorePropertiesDto StorePropertiesDto);
}
