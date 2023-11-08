package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.MessageRequestDto;
import com.br.personniMoveis.dto.User.UserAdminCreateAccountDto;
import com.br.personniMoveis.dto.User.UserCreateAccountDto;
import com.br.personniMoveis.dto.User.UserGetDto;
import com.br.personniMoveis.dto.UserAdminInfo;
import com.br.personniMoveis.dto.UserUpdateInfoDto;
import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.model.user.ClientAddress;
import com.br.personniMoveis.repository.UserRepository;
import com.br.personniMoveis.service.EmailService;
import com.br.personniMoveis.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final UserRepository userRepository;

    @Autowired
    public UserController(EmailService emailService, UserService userService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userService = userService;
        this.userRepository = userRepository;
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

    // Metodo para pegar info do usuario logado
    @GetMapping(path = "/user-info")
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserGetDto> getUserById(@RequestHeader("Authorization") String token) {
        UserGetDto user = userService.getUser(token);
        return ResponseEntity.ok(user);
    }

    @GetMapping(path = "/get-user-address/{addressId}")
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ClientAddress> getSingleAddress(@RequestHeader("Authorization") String token, @PathVariable Long addressId) {
        ClientAddress clientAddress = userService.getSingleAddress(token, addressId);
        return ResponseEntity.ok(clientAddress);
    }

    @PutMapping("/edit-user-address")
    public ResponseEntity<String> updateAddress(@RequestHeader("Authorization") String token, @PathVariable Long addressId, @RequestBody ClientAddress updatedAddress) {
        try {
            userService.updateAddress(token, addressId, updatedAddress);
            return ResponseEntity.ok("Endereço atualizado com sucesso.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping(path = "/update-user")
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserUpdateInfoDto userUpdateInfoDto, @RequestHeader("Authorization") String token) throws ChangeSetPersister.NotFoundException {
        userService.updateUserInfo(userUpdateInfoDto, token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

    @GetMapping(path = "/get-user-address")
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClientAddress>> getClientAddresses(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getAllUserAddresses(token));
    }

    // Metodo para pegar info de um usuario da tela de Admin
    @GetMapping(path = "/adminUser-info/{userId}")
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAdminInfo> adminGetUser(@PathVariable Long userId) {
        var user = userRepository.getReferenceById(userId);
        return ResponseEntity.ok(new UserAdminInfo(user));
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

    @PutMapping(path = "/admin-update-user")
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> adminUpdateUser(@RequestBody UserAdminInfo userAdminInfo) {
        userService.AdminUpdateUserInfo(userAdminInfo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{userId}")
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> adminDeleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/delete-user-address/{addressId}")
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ClientAddress> deleteUserAddress(@RequestHeader("Authorization") String token, @PathVariable Long addressId) {
        userService.deleteUserAddress(token, addressId);
        return ResponseEntity.noContent().build();
    }
}
