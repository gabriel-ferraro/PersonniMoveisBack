package com.br.personniMoveis.mapper.product;

import com.br.personniMoveis.dto.product.ProductDto;
import com.br.personniMoveis.dto.product.ProductPutDto;
import com.br.personniMoveis.dto.product.get.ProductGetDto;
import com.br.personniMoveis.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapeamento para dtos de Product.
 */
@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    public abstract Product productDtoToProduct(ProductDto productDto);

    public abstract Product productPutDtoToProduct(ProductPutDto productDto);

    public abstract ProductGetDto productToProductGetDto(Product product);
}
