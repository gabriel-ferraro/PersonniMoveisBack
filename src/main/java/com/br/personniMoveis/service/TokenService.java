package com.br.personniMoveis.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.br.personniMoveis.model.user.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {


    @Value("{api.security.token.secret}") // Passando o valor da senha em properties para a variavel.
    private String secret;

    /**
     * Retorna o token JWT - é gerado com o papel, id do usuário e email. É atribuído 48h para expirar o token.
     *
     * @param user Usuário detentor do token.
     * @return O token JWT (como uma String) com informações necessárias para identificar o usuário no front.
     */
    public String generateToken(UserEntity user) {
        try {
            var algorithm = Algorithm.HMAC256(secret); // Senha da API
            return JWT.create()
                    .withIssuer("PersonniMoveis API")
                    .withSubject(user.getEmail())
                    .withClaim("userId", user.getUserId())
                    .withClaim("userRole", user.getProfile().toString())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error at generating JWT Token", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("PersonniMoveis API")
                    .build().verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    /**
     * Adquire dados do token recebido na requisição, com base na chave recebida como parâmetro.
     *
     * @param tokenJWT Token recebido.
     * @param key      Nome do atributo que se deseja extrair do token (id, role, etc...);
     *                 Se a chave informada não existir no token, retorna null.
     * @return Valor referente ao parâmetro key ou null se chave não existe.
     */
    public String getClaimFromToken(String tokenJWT, String key) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("PersonniMoveis API")
                    .build().verify(tokenJWT)
                    .getClaim(key).toString();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!!");
        }
    }

    /**
     * Devolve + 2 dias a partir do momento que o token é criado. Seta horas para o timezone atual do Brasil(-3h).
     *
     * @return Retorna o tempo do token como + 2 dias do momento que é criado.
     */
    private Instant expirationDate() {
        return LocalDateTime.now().plusDays(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
