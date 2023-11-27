package com.br.personniMoveis;

import com.br.personniMoveis.constant.Profiles;
import com.br.personniMoveis.dto.CategoryDto.CategoryCmpDto;
import com.br.personniMoveis.dto.User.UserAdminCreateAccountDto;
import com.br.personniMoveis.model.product.Detail;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.store.StoreProperties;
import com.br.personniMoveis.service.CategoryService;
import com.br.personniMoveis.service.StorePropertiesService;
import com.br.personniMoveis.service.UserService;
import com.br.personniMoveis.service.product.DetailService;
import com.br.personniMoveis.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Roda "rotina" para criar dados fake da loja.
 */
@Component
public class DataPopulator implements CommandLineRunner {

    private final UserService userService;
    private final StorePropertiesService storePropertiesService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final DetailService detailService;


    @Autowired
    public DataPopulator(UserService userService, StorePropertiesService storePropertiesService, ProductService productService, CategoryService categoryService, DetailService detailService) {
        this.userService = userService;
        this.storePropertiesService = storePropertiesService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.detailService = detailService;
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

        // Cria detalhe da pltropna Vox.
        Detail detail1 = Detail.builder()
                .detailField("Material resistente e agradável")
                .fieldContent("Material.")
                .build();

        Set<Detail> details1 = new HashSet<>();
        details1.add(detail1);
        detailService.saveDetail(detail1);
        // Cria poltrona Vox.
        var poltronaVox = Product.builder()
                .name("Poltrona Vox")
                .value(335D)
                .quantity(6L)
                .editable(false)
                .available(true)
                .description("Com um desenho pautado na inovação, a poltrona Vox combina influências do design contemporâneo para criar uma silhueta única e magnética. Convidando o olhar para o encanto e admiração com sua forma redonda, essa peça serve como um elemento de estilo e destaque no espaço")
                .details(details1)
                .build();
        var newPoltrona = productService.createFullProduct(poltronaVox, newPoltronaCat.getId());

        // Insere imagens na poltrona
        newPoltrona.setMainImg("https://docs.google.com/uc?id=1ZX7PAsj5iNMuju7693Byy8YMxVpiV9a8");

        productService.updateProduct(newPoltrona, newPoltronaCat.getId());

        //
        // Cria detalhe da pltropna 2.
        Detail detail2 = Detail.builder()
                .fieldContent("Material")
                .detailField("Suave e aconchegante")
                .build();

        Set<Detail> details2 = new HashSet<>();
        details2.add(detail2);
        detailService.saveDetail(detail2);
        // Cria poltrona Vox.
        var poltronaGioconda = Product.builder()
                .name("Poltrona Gioconda")
                .value(277D)
                .quantity(4L)
                .description("Poltrona estilosa para descansar, no conforto da sua casa.")
                .editable(false)
                .available(true)
                .details(details2)
                .build();
        var newPoltronaGioconda = productService.createFullProduct(poltronaGioconda, newPoltronaCat.getId());

        // Insere imagens na poltrona
        newPoltronaGioconda.setMainImg("https://docs.google.com/uc?id=1RgAa5hFm1fPT-KSxi6oq3WemAeisWZEV");

        productService.updateProduct(newPoltronaGioconda, newPoltronaCat.getId());


        //
        // Cria detalhe da pltropna 3.
        Detail detail3 = Detail.builder()
                .fieldContent("Poltrona elegante")
                .detailField("Perfeita para ambientes onde o conforto e o estilo são necessários.")
                .build();

        Set<Detail> details3 = new HashSet<>();
        details3.add(detail3);
        detailService.saveDetail(detail2);
        // Cria poltrona Vox.
        var poltronaVerona = Product.builder()
                .name("Poltrona Verona")
                .value(295.80D)
                .quantity(3L)
                .description("Poltrona excelente para ambiente elegantes.")
                .editable(false)
                .available(true)
                .details(details3)
                .build();
        var newpoltronaVerona = productService.createFullProduct(poltronaVerona, newPoltronaCat.getId());

        // Insere imagens na poltrona
        newpoltronaVerona.setMainImg("https://docs.google.com/uc?id=18TY7JqeF2PgLO8QjeGqcno9I3vopK140");

        productService.updateProduct(newpoltronaVerona, newPoltronaCat.getId());

        //
        // Cria categoria de Armários.
        var armarios = CategoryCmpDto.builder()
                .name("Armarios")
                .allow_creation(true)
                .build();
        var newArmarioCat = categoryService.createCategoryCmp(armarios);

        // Cria categoria de Armários.
        var guardaroupa = CategoryCmpDto.builder()
                .name("Guarda Roupas")
                .allow_creation(true)
                .build();
        var newGuardaroupa = categoryService.createCategoryCmp(guardaroupa);
        //
        // Cria detalhe de armario 4.
        Detail detail4 = Detail.builder()
                .fieldContent("Guarda-roupa feito com madeira ecológica")
                .detailField("Produzido com madeira de reflorestamenteo, ecológicamente correto e belo para sua vontade.")
                .build();

        Set<Detail> details4 = new HashSet<>();
        details4.add(detail4);
        detailService.saveDetail(detail4);

        var armarioIpanema = Product.builder()
                .name("Guarda Roupa Ipanema")
                .value(599.80D)
                .quantity(3L)
                .description("Guarda Roupas excelente para sua casa e ambientes tranquilos.")
                .editable(false)
                .available(true)
                .details(details4)
                .build();
        var newArmarioIpanema = productService.createFullProduct(armarioIpanema, newGuardaroupa.getId());

        // Insere imagens na poltrona
        newArmarioIpanema.setMainImg("https://docs.google.com/uc?id=1bCTiLFw76-FdDrQLlgHKEgjuTPRvFivr");

        productService.updateProduct(newArmarioIpanema, newGuardaroupa.getId());

        //
        // Cria detalhe de armario 5.
        Detail detail5 = Detail.builder()
                .fieldContent("Guarda-roupa com madeira ecológica")
                .detailField("Produzido com madeira de reflorestamenteo, ecológicamente correto e belo para sua vontade.")
                .build();

        Set<Detail> details5 = new HashSet<>();
        details4.add(detail5);
        detailService.saveDetail(detail5);
        // Cria poltrona Vox.
        var armarioBeatriz = Product.builder()
                .name("Guarda Roupa Beatriz")
                .value(600.80D)
                .quantity(3L)
                .description("Guarda-roupa para sua casa e ambientes tranquilos.")
                .editable(false)
                .available(true)
                .details(details5)
                .build();
        var newArmarioBeatriz = productService.createFullProduct(armarioBeatriz, newGuardaroupa.getId());

        // Insere imagens na poltrona
        newArmarioBeatriz.setMainImg("https://docs.google.com/uc?id=15IXx6q58rqMGOcrEQQgeO2uZlKaY-phW");

        productService.updateProduct(newArmarioBeatriz, newGuardaroupa.getId());


        //
        // Cria detalhe de armario 6.
        Detail detail6 = Detail.builder()
                .fieldContent("Guarda-roupa com madeira ecológica")
                .detailField("Produzido com madeira de reflorestamenteo, ecológicamente correto e belo para sua vontade.")
                .build();

        Set<Detail> details6 = new HashSet<>();
        details4.add(detail6);
        detailService.saveDetail(detail5);
        // Cria poltrona Vox.
        var grGalileu = Product.builder()
                .name("Guarda Roupa Galileu")
                .value(350.70D)
                .quantity(3L)
                .description("Guarda-roupa excelente para sua casa e ambientes tranquilos.")
                .editable(false)
                .available(true)
                .details(details6)
                .build();
        var newgrGalileu = productService.createFullProduct(grGalileu, newGuardaroupa.getId());

        // Insere imagens na poltrona
        newgrGalileu.setMainImg("https://docs.google.com/uc?id=1iqwwc3XDODv-34n2hdSbtQ66wDE5_ijH");

        productService.updateProduct(newgrGalileu, newGuardaroupa.getId());



    }
}
