package com.br.personniMoveis.service;

import com.br.personniMoveis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// Spring chama automaticamente o service na hora da autenticação
@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override // Spring chama metodo automaticamente passando o "username" ou "login" digitado no formulario de login.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username); // Retorna o usuario do banco de dados
    }

}
