package com.br.personniMoveis.mapper.product;

import com.br.personniMoveis.dto.product.ProductDto;
import com.br.personniMoveis.dto.product.ProductGetDto;
import com.br.personniMoveis.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapeamento para dtos de Product.
 */
@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "productId", ignore = true)
    public abstract Product toProduct(ProductDto productDto);
    
    public abstract ProductGetDto productToProductGetDto(Product product);
}