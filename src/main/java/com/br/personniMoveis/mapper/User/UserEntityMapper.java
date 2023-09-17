package com.br.personniMoveis.mapper.User;

import com.br.personniMoveis.dto.User.UserCreateAccountDto;
import com.br.personniMoveis.dto.product.DetailDto;
import com.br.personniMoveis.mapper.ElementCmp.ElementCmpMapper;
import com.br.personniMoveis.model.product.Detail;
import com.br.personniMoveis.model.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UserEntityMapper {

    public static final UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    public abstract UserEntity userCreateAccountDtoToUserEntity(UserCreateAccountDto userCreateAccountDto);

}
