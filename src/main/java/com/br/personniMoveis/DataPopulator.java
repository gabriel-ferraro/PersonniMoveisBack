package com.br.personniMoveis;

import com.br.personniMoveis.constant.Profiles;
import com.br.personniMoveis.dto.CategoryDto.CategoryCmpDto;
import com.br.personniMoveis.dto.User.UserAdminCreateAccountDto;
import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.store.StoreProperties;
import com.br.personniMoveis.service.CategoryService;
import com.br.personniMoveis.service.StorePropertiesService;
import com.br.personniMoveis.service.UserService;
import com.br.personniMoveis.service.product.ProductService;
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
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public DataPopulator(UserService userService, StorePropertiesService storePropertiesService, ProductService productService, CategoryService categoryService) {
        this.userService = userService;
        this.storePropertiesService = storePropertiesService;
        this.productService = productService;
        this.categoryService = categoryService;
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
                .name("Gabriel Ferraro Severino")
                .email("gabrielferraro00@gmail.com")
                .password("123")
                .cpf("62233746074")
                .phoneNumber("41922516269")
                .profile(Profiles.USER)
                .build();
        userService.adminCreateAccount(normalUser);

        // Cria user USER 2.
        UserAdminCreateAccountDto normalUser2 = UserAdminCreateAccountDto.builder()
                .name("Athos Matovani")
                .email("athos@gmail.com")
                .password("123")
                .cpf("12669988962")
                .phoneNumber("41922517548")
                .profile(Profiles.USER)
                .build();
        userService.adminCreateAccount(normalUser2);

        // Cria categoria de poltronas.
        var poltronas = CategoryCmpDto.builder()
                .name("Poltronas")
                .allow_creation(false)
                .build();
        var newPoltronaCat = categoryService.createCategoryCmp(poltronas);

        // Cria poltrona Vox.
        var poltronaVox = Product.builder()
                .name("Poltrona Vox")
                .value(335D)
                .quantity(6L)
                .description("Poltrona legal")
                .editable(false)
                .available(true)
                .build();
        var newPoltrona = productService.createFullProduct(poltronaVox, newPoltronaCat.getId());

        // Insere imagens na poltrona
        newPoltrona.setMainImg("https://docs.google.com/uc?id=1r6_vKbulYFfXOenjxBx29hK8eJTR20g3");
        productService.updateProduct(newPoltrona, newPoltronaCat.getId());

        // Cria uma mesa Vox.
//        var prod = Product.builder()productService
//
//        productService.createFullProduct();
    }
}
