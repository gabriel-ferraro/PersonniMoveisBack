package com.br.personniMoveis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Contém a implementação de configurações default para aplicação e requisições.
 */
@Configuration
public class PersonniMoveisWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolver) {
        // Configuração no handler de paginação
        PageableHandlerMethodArgumentResolver pageHandler = new PageableHandlerMethodArgumentResolver();
        // Por padrão, sempre que houver um endpoint que devolve um elemento paginado, 
        // A paginação começa na página 0 e a qtde de elementos é 20.
        pageHandler.setFallbackPageable(PageRequest.of(0, 20));

        // Configurações adicionadas ao resolver:
        resolver.add(pageHandler);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Inserir porta do front-end
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
    }
}
