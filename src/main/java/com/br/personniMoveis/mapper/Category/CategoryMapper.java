package com.br.personniMoveis.mapper.Category;

import com.br.personniMoveis.dto.CategoryDto.CategoryGetByIdDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPostDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPutDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.model.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    public abstract Category toCategory(CategoryPutDto categoryPutDto);

    @Mapping(target = "categoryId", ignore = true)
    public abstract Category toCategoryPost(CategoryPostDto categoryPostDto);

    @Mapping(target = "categoryId", ignore = true)
    public abstract Category toCategoryPut(CategoryPutDto categoryPutDto);

    public abstract CategoryGetDto CategotyToCategoryGetDto(Category category);

    public abstract CategoryGetByIdDto CategotyToCategoryGetByIdDto(Category category);
}
