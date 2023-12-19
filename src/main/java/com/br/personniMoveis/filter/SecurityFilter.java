package com.br.personniMoveis.filter;

import com.br.personniMoveis.repository.UserRepository;
import com.br.personniMoveis.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = retrieveToken(request);
        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT); // Pega o token gerado
            var user = userRepository.findByEmail(subject); // Pega o usuario (email) pelo token gerado
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); // Cria a autenticação para o usuario
            SecurityContextHolder.getContext().setAuthentication(authentication); // Faz a autenticação do usuario
        }

        filterChain.doFilter(request, response); //necessario para chamar os próximos filtros na aplicação
    }

    private String retrieveToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
