package com.br.personniMoveis.mapper;

import com.br.personniMoveis.dto.product.post.TagDto;
import com.br.personniMoveis.model.product.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapeamento para tag do produto.
 */
@Mapper(componentModel = "spring")
public abstract class TagMapper {

    public static final TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    public abstract Tag tagDtoToTag(TagDto tagDto);
}
