package com.br.personniMoveis.mapper.product;

import com.br.personniMoveis.dto.product.DetailDto;
import com.br.personniMoveis.dto.product.ProductDto;
import com.br.personniMoveis.dto.product.get.ProductGetDto;
import com.br.personniMoveis.model.product.Detail;
import com.br.personniMoveis.model.product.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapeamento para dtos de Product.
 */
@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

//    @Mapping(source = "detailList", target = "details")
//    @Mapping(source = "materialList", target = "materials")
//    @Mapping(source = "tagList", target = "tags")
//    @Mapping(source = "sectionList", target = "sections")
    public abstract Product productDtoToProduct(ProductDto productDto);

    public abstract ProductGetDto productToProductGetDto(Product product);
}
