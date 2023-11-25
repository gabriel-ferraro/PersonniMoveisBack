package com.br.personniMoveis.dto.User;

import com.br.personniMoveis.constant.Profiles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAdminCreateAccountDto {

    private String name;

    private String email;

    private String password;

    private String cpf;

    private String phoneNumber;

    private Profiles profile;

}
