package com.br.personniMoveis.mapper.Category;

import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryCmpDto;
import com.br.personniMoveis.dto.product.CategoryDto;
import com.br.personniMoveis.model.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    public abstract Category categoryDtoToCategory(CategoryDto categoryDto);

    public abstract Category categoryCmpDtoToCategory(CategoryCmpDto categorycmpDto);

    public abstract CategoryGetDto CategotyToCategoryGetDto(Category category);
}
