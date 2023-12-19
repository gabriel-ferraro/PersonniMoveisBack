package com.br.personniMoveis.dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateAccountDto {

    private String name;

    private String email;

    private String password;

    private String cpf;

    private String phoneNumber;
}
