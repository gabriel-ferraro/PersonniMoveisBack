package com.br.personniMoveis.mapper.Category;

import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPostDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPutDto;
import com.br.personniMoveis.dto.product.post.CategoryDto;
import com.br.personniMoveis.model.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "categoryId", ignore = true)
    public abstract Category categoryProductPostDtoToCategory(CategoryDto categoryDto);

    public abstract com.br.personniMoveis.dto.product.get.CategoryDto categoryToCategoryList(Category category);

    public abstract CategoryGetDto categoryToCategoryGetDto(Category category);

    @Mapping(target = "categoryId", ignore = true)
    public abstract Category categoryPutDtoToCategory(CategoryPutDto categoryPutDto);

    @Mapping(target = "categoryId", ignore = true)
    public abstract Category categoryPostDtoToCategory(CategoryPostDto categoryPostDto);
}
