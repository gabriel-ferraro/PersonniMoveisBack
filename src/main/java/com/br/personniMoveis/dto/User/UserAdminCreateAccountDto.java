package com.br.personniMoveis.dto.User;

import com.br.personniMoveis.constant.Profiles;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAdminCreateAccountDto {

    private String name;

    private String email;

    private String password;

    private String cpf;

    private String phoneNumber;

    private Profiles profile;

}
