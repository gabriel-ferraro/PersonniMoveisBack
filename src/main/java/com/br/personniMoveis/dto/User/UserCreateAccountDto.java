package com.br.personniMoveis.dto.User;

import com.br.personniMoveis.constant.Profiles;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
