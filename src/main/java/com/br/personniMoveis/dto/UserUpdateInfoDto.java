package com.br.personniMoveis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateInfoDto {

    private String name;

    private String email;

    private String cpf;

    private String phoneNumber;

    private String currentPassword;

    private String newPassword;

}
