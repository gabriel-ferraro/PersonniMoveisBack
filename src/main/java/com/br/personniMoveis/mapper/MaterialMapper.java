package com.br.personniMoveis.mapper;

import com.br.personniMoveis.dto.product.post.MaterialDto;
import com.br.personniMoveis.model.product.Material;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapeamento para material do produto.
 */
@Mapper(componentModel = "spring")
public abstract class MaterialMapper {

    public static final MaterialMapper INSTANCE = Mappers.getMapper(MaterialMapper.class);

    @Mapping(target = "materialId", ignore = true)
    public abstract Material materialDtoToMaterial(MaterialDto materialDto);
}
