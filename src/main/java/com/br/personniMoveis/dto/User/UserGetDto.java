package com.br.personniMoveis.dto.User;


import com.br.personniMoveis.enums.Profiles;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserGetDto {

    private Long userId;

    private String name;

    private String email;

    private String cpf;

    private String phoneNumber;

    private Profiles profile;

}