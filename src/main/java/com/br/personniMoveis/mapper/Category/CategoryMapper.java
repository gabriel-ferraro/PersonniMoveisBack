package com.br.personniMoveis.mapper.Category;

import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPostDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPutDto;
import com.br.personniMoveis.dto.product.CategoryDto;
import com.br.personniMoveis.model.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    public abstract Category categoryProductPostDtoToCategory(CategoryDto categoryDto);

    public abstract CategoryDto categoryToCategoryList(Category category);

    public abstract CategoryGetDto categoryToCategoryGetDto(Category category);

    public abstract Category categoryPutDtoToCategory(CategoryPutDto categoryPutDto);

    public abstract Category categoryPostDtoToCategory(CategoryPostDto categoryPostDto);
}
