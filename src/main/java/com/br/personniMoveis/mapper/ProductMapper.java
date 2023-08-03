package com.br.personniMoveis.mapper;

import com.br.personniMoveis.dto.ProductPostDto;
import com.br.personniMoveis.dto.ProductPutDto;
import com.br.personniMoveis.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "productId", ignore = true)
    public abstract Product toProduct(ProductPostDto productPostDto);

    public abstract Product toProduct(ProductPutDto productPutDto);
}
