package com.br.personniMoveis.mapper.ProductCmp;

import com.br.personniMoveis.dto.ProductCmp.ProductCmpDto;
import com.br.personniMoveis.dto.ProductCmp.ProductCmpGetDto;
import com.br.personniMoveis.model.productCmp.ProductCmp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ProductCmpMapper {
    public static final ProductCmpMapper INSTANCE = Mappers.getMapper(ProductCmpMapper.class);

    @Mapping(target = "productCmpId", ignore = true)
    public abstract ProductCmp toProductCmp(ProductCmpDto productCmpDtoCmp);

    public abstract ProductCmpGetDto ProductCmpToProductCmpGetDto(ProductCmp productCmp);
}