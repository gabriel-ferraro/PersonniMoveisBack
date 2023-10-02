package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.MessageRequestDto;
import com.br.personniMoveis.dto.User.UserAdminCreateAccountDto;
import com.br.personniMoveis.dto.User.UserCreateAccountDto;
import com.br.personniMoveis.dto.User.UserGetDto;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.user.UserEntity;
import com.br.personniMoveis.service.EmailService;
import com.br.personniMoveis.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controladora do usuário. Define métodos para autenticação, login, logout
 * alteração e visualização de dados, etc. Validações enviadas para o usuário
 * por e-mail são feitas com o "nosso" e-mail: personnimoveis@gmail.com - senha
 * de acesso: PersonniMoveis123 - A loja que utiliza o sistema pode modificar o
 * e-mail via enpoint em StorePropertiesController.
 */
@RestController
@RequestMapping("users")
public class UserController {

    private final EmailService emailService;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(EmailService emailService, UserService userService, PasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/create-account")
    public ResponseEntity<HttpStatus> createUserAccount(@RequestBody @Valid UserCreateAccountDto createAccountDto) {
//        var password = passwordEncoder.encode(createAccountDto.getPassword());
        userService.createAccount(createAccountDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserGetDto>> getAllUsers() {
        List<UserGetDto> Users = userService.getAllUsers();
        return ResponseEntity.ok(Users);
    }

    @PostMapping(path = "/admin-create-account")
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> adminCreateAccount(@RequestBody @Valid UserAdminCreateAccountDto userAdminCreateAccountDto) {
        userService.adminCreateAccount(userAdminCreateAccountDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Faz validação da conta do usuário pelo número de celular.
     *
     * @param confirmationCode Código de confirmação para validar conta.
     * @return
     */
//    @PostMapping
//    public ResponseEntity confirmUserAccountByPhone(String confirmationCode) {
//
//    }
    /**
     * Faz validação da conta do usuário pelo e-email.
     *
     * @param confirmationCode Código de confirmação para validar conta.
     * @return
     */
//    @PostMapping
//    public ResponseEntity confirmUserAccountByEmail(String confirmationCode) {
//
//    }
    /**
     * Retorna os produtos adquiridos pelo cliente.
     *
     * @return Retorna os produtos adquiridos pelo cliente.
     */
//    @GetMapping(path = "products-from-client")
//    public ResponseEntity getProductsFromClient() {
//
//    }

    /**
     * Retorna todos "pedidos" do cliente contendo os produtos respectivos.
     *
     * @return Todos "pedidos" do cliente contendo os produtos respectivos.
     */
//    @GetMapping(path = "orders-from-client")
//    public ResponseEntity getOrdersFromClient() {
//
//    }
    @Operation(summary = "Envia e-mail de aviso sobre retorno do produto à loja para todos clientes que aguardavam.")
    @PostMapping(path = "/product-wait-list-email")
    public ResponseEntity<HttpStatus> productWaitListUpdateEmail(@RequestBody @Valid Product product) {
        // Adquire lista de todos clientes que esperam pelo produto.
        List<UserEntity> waitingClients = userService.getClientsWaitingForProduct(product.getProductId());
        // Envia e-mail para todos clientes notificando a volta do produto.
        waitingClients.forEach(
                client -> this.emailService.productArrivedMessage(
                        client.getEmail(), "Personni Móveis", product, client.getName(),
                        "http://localhost:8080/produtos/".concat(String.valueOf(product.getProductId()))));
        // Retorna sucesso.
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Método para testar envio de um email configurado para gerar conteúdo
     * MIME.
     *
     * @param request Dto com conteúdo para envio da mensagem.
     * @return Http status 204 - No Content.
     */
    @PostMapping(path = "/test-message")
    public ResponseEntity<HttpStatus> testMessageToUserEmail(@RequestBody @Valid MessageRequestDto request) {
        emailService.test(request.getTo(), request.getStoreName(), request.getClientName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
