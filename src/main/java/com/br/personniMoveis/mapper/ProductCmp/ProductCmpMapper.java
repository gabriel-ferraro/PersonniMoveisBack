package com.br.personniMoveis.mapper.ProductCmp;

import com.br.personniMoveis.dto.ProductCmp.ProductCmpDto;
import com.br.personniMoveis.dto.ProductCmp.ProductCmpGetByIdDto;
import com.br.personniMoveis.dto.ProductCmp.ProductCmpGetDto;
import com.br.personniMoveis.model.productCmp.ProductCmp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ProductCmpMapper {
    public static final ProductCmpMapper INSTANCE = Mappers.getMapper(ProductCmpMapper.class);

    public abstract ProductCmp toProductCmp(ProductCmpDto productCmpDtoCmp);

    public abstract ProductCmpDto cmpToDto(ProductCmp productCmp);

    public abstract ProductCmpGetDto ProductCmpToProductCmpGetDto(ProductCmp productCmp);

    public abstract ProductCmpGetByIdDto ProductCmpToProductCmpGetByIdDto(ProductCmp productCmp);
}
