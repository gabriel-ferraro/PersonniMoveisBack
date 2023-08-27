package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.MessageRequestDto;
import com.br.personniMoveis.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public UserController(EmailService emailService) {
        this.emailService = emailService;
    }

//    @PostMapping
//    public ResponseEntity createAccount() {
//
//    }
//
//    @PutMapping
//    public ResponseEntity updateAccountData() {
//
//    }
//
//    @DeleteMapping
//    public ResponseEntity DeleteAccount() {
//
//    }
//
//    @PostMapping(path = "/login")
//    public ResponseEntity doLogin() {
//
//    }
//
//    @PostMapping(path = "/logout")
//    public ResponseEntity doLogout() {
//
//    }
//    @PutMapping(path = "change-user-password")
//    public ResponseEntity changeUserPassword() {
//
//    }
//    @PutMapping(path = "change-phone")
//    public ResponseEntity changeUserPhone() {
//
//    }
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

    /**
     * Método para testar envio de um email configurado para gerar conteúdo
     * MIME.
     *
     * @param request Dto com conteúdo para envio da mensagem.
     * @return Http status 204 - No Content.
     */
    @PostMapping(path = "test-message")
    public ResponseEntity<HttpStatus> testMessageToUserEmail(@RequestBody @Valid MessageRequestDto request) {
        emailService.test(request.getTo(), request.getStoreName(), request.getClientName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
