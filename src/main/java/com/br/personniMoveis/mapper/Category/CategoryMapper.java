package com.br.personniMoveis.mapper.Category;

import com.br.personniMoveis.dto.CategoryDto.CategoryDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetByIdDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.model.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "id", ignore = true)
    public abstract Category toCategory(CategoryDto categoryDto);

    public abstract CategoryGetDto CategotyToCategoryGetDto(Category category);

    public abstract CategoryGetByIdDto CategotyToCategoryGetByIdDto(Category category);
}
