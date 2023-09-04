package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.AuthenticationDto;
import com.br.personniMoveis.dto.JWTDto;
import com.br.personniMoveis.model.user.UserEntity;
import com.br.personniMoveis.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> requestLogin(@RequestBody @Valid AuthenticationDto data) {
        var authToken = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());// Representa o login e senha do usuario
        var authentication = manager.authenticate(authToken); // Metodo que autentica o login e senha passados
        var tokenJWT = tokenService.generateToken((UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(new JWTDto(tokenJWT));
    }

}
