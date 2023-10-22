package com.br.personniMoveis.mapper.Category;

import com.br.personniMoveis.dto.CategoryDto.CategoryCmpDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetByIdDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.dto.product.CategoryDto;
import com.br.personniMoveis.model.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    public abstract Category categoryDtoToCategory(CategoryDto categoryDto);

    @Mapping(source = "productList", target = "products")
    public abstract Category categoryDtoToCategoryTest(CategoryDto categoryDto);

    @Mapping(target = "id", ignore = true)
    public abstract Category categoryCmpDtoToCategory(CategoryCmpDto categoryCmpDto);

    @Mapping(target = "id", ignore = true)
    public abstract Category categoryCmpToCategory(Category category);

    public abstract CategoryGetDto CategoryToCategoryGetDto(Category category);

    public abstract CategoryGetByIdDto CategotyToCategoryGetByIdDto(Category category);
}
