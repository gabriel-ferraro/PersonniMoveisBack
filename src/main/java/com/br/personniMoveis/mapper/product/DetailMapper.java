package com.br.personniMoveis.mapper.product;

import com.br.personniMoveis.dto.product.DetailGetDto;
import com.br.personniMoveis.dto.product.DetailPostDto;
import com.br.personniMoveis.model.product.Detail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class DetailMapper {

    public static final DetailMapper INSTANCE = Mappers.getMapper(DetailMapper.class);

    public abstract Detail toDetail(DetailPostDto detailPostDto);
    public abstract DetailGetDto detailToDetailGetDto(Detail detail);
}
