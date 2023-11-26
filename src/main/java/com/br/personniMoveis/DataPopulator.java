package com.br.personniMoveis;

import com.br.personniMoveis.constant.Profiles;
import com.br.personniMoveis.dto.User.UserAdminCreateAccountDto;
import com.br.personniMoveis.model.store.StoreProperties;
import com.br.personniMoveis.service.StorePropertiesService;
import com.br.personniMoveis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Roda "rotina" para criar dados fake da loja.
 */
@Component
public class DataPopulator implements CommandLineRunner {

    private final UserService userService;
    private final StorePropertiesService storePropertiesService;

    @Autowired
    public DataPopulator(UserService userService, StorePropertiesService storePropertiesService) {
        this.userService = userService;
        this.storePropertiesService = storePropertiesService;
    }

    @Override
    public void run(String... args) throws Exception {

        // Cria configs da loja.
        StoreProperties storeProp = StoreProperties.builder()
                .storeId(1L)
                .storeName("Personni móveis")
                .storeEmail("personnimoveis@gmail.com")
                .storePhone("(41) 99999-9999")
                .primaryCollor("#B68D40")
                .secondaryCollor("#112620")
                .siteContext("http://localhost:8080/")
                .aboutUsInfo("Bem-vindo à Personni móveis, onde a personalização e modelagem de móveis são a essência do nosso trabalho. Transformamos espaços com soluções sob medida, refletindo o estilo de cada cliente. ")
                .build();
        storePropertiesService.updateStore(storeProp);

        // Cria user admin.
        UserAdminCreateAccountDto admin = UserAdminCreateAccountDto.builder()
                .name("ADMIN")
                .email("personnimoveis@gmail.com")
                .password("123")
                .cpf("56789389083")
                .phoneNumber("41922293824")
                .profile(Profiles.ADMIN)
                .build();
        userService.adminCreateAccount(admin);

        // Cria user colab.
        UserAdminCreateAccountDto colaborator = UserAdminCreateAccountDto.builder()
                .name("Manoel")
                .email("manoel@personni.com")
                .password("123")
                .cpf("67819706074")
                .phoneNumber("41925611382")
                .profile(Profiles.COLLABORATOR)
                .build();
        userService.adminCreateAccount(colaborator);

        // Cria user USER.
        UserAdminCreateAccountDto normalUser = UserAdminCreateAccountDto.builder()
                .name("João")
                .email("gabrielferraro00@gmail.com")
                .password("123")
                .cpf("62233746074")
                .phoneNumber("41922516269")
                .profile(Profiles.USER)
                .build();
        userService.adminCreateAccount(normalUser);
    }
}
