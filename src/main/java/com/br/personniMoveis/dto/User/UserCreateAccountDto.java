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


//    private Profiles profile;
//
//    public UserCreateAccountDto() {
//        this.profile = Profiles.USER;
//    }

//    public void setProfile(Profiles profile) {
//        if (profile == null || profile.toString().trim().isEmpty()) {
//            this.profile = null;
//        } else {
//            this.profile = profile;
//        }
//    }

}
