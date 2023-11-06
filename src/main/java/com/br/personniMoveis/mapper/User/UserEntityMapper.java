package com.br.personniMoveis.mapper.User;

import com.br.personniMoveis.dto.User.UserCreateAccountDto;
import com.br.personniMoveis.dto.User.UserGetDto;
import com.br.personniMoveis.dto.UserUpdateInfoDto;
import com.br.personniMoveis.model.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UserEntityMapper {

    public static final UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    public abstract UserEntity userCreateAccountDtoToUserEntity(UserCreateAccountDto userCreateAccountDto);

    public abstract UserGetDto UserEntityToUserGetDto(UserEntity user);

    public abstract UserGetDto UserEntityToUserGetInfoDto(UserEntity user);

    public abstract UserEntity userGetInfoDtoToUser(UserUpdateInfoDto userGetInfoDto);

}