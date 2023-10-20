package com.br.personniMoveis.utils;

import com.br.personniMoveis.exception.UnauthorizedRequestException;
import com.br.personniMoveis.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Método utiliztários para autenticação.
 */
@Component
public class AuthUtils {

    private final TokenService tokenService;

    @Autowired
    public AuthUtils(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Identifica o id do usuário via claim do token.
     *
     * @param token token do user autenticado.
     * @return retorna o id do user.
     */
    public Long getUserId(String token) {
        return Long.valueOf(tokenService.getClaimFromToken(token, "userId"));
    }

    /**
     * identifica se usuário tem permissão de admin para fazer a requisição
     */
    public void validateUserAdmin(String token) throws UnauthorizedRequestException {
        // Se usuário não é admin, joga exceção de não autorizado.
        if (!Objects.equals(tokenService.getClaimFromToken(token, "userRole"), "ADMIN")) {
            throw new UnauthorizedRequestException("Usuário não tem permissão de acesso.");
        }
    }

}
