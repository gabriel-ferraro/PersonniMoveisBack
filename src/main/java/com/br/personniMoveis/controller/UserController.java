package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.MessageRequestDto;
import com.br.personniMoveis.dto.User.UserAdminCreateAccountDto;
import com.br.personniMoveis.dto.User.UserCreateAccountDto;
import com.br.personniMoveis.dto.User.UserGetDto;
import com.br.personniMoveis.model.user.ClientAddress;
import com.br.personniMoveis.service.EmailService;
import com.br.personniMoveis.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(EmailService emailService, UserService userService, PasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/create-account")
    public ResponseEntity<HttpStatus> createUserAccount(@RequestBody @Valid UserCreateAccountDto createAccountDto) {
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

    @PostMapping(path = "/create-new-address")
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ClientAddress> createNewAddress(@RequestHeader("Authorization") String token, @RequestBody @Valid ClientAddress address) {
        return ResponseEntity.ok(userService.createAddress(token, address));
    }

    @GetMapping(path = "/get-user-address/{userId}")
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClientAddress>> getClientAddresses(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getAllUserAddresses(userId));
    }
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
