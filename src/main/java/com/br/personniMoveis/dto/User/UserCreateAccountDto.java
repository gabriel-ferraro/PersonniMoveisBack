package com.br.personniMoveis.dto.User;

import com.br.personniMoveis.constant.Profiles;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateAccountDto {

    private String name;

    private String email;

    private String password;

    private String cpf;

    private String phoneNumber;

    private Profiles profile;

}
