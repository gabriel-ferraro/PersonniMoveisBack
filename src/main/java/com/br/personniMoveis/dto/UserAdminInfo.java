package com.br.personniMoveis.dto;

import com.br.personniMoveis.enums.Profiles;
import com.br.personniMoveis.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminInfo {

    private Long userId;

    private String name;

    private String email;

    private String cpf;

    private String phoneNumber;

    private Profiles profile;

    public UserAdminInfo(UserEntity user) {
        this(user.getUserId(), user.getName(), user.getEmail(), user.getCpf(), user.getPhoneNumber(), user.getProfile());
    }
}