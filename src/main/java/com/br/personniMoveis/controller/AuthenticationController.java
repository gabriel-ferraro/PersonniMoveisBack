package com.br.personniMoveis.controller;

import com.br.personniMoveis.dto.AuthenticationDto;
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

    @PostMapping
    public ResponseEntity<?> requestLogin(@RequestBody @Valid AuthenticationDto data) {
        var token = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());// Representa o login e senha do usuario
        var authentication = manager.authenticate(token); // Metodo que autentica o login e senha passados
        return ResponseEntity.ok().build();
    }

}
