package com.br.personniMoveis.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
/**
 * Contém a implementação de configurações default para aplicação e requisições.
 */
public class PersonniMoveisWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolver) {
        // Configuração no handler de paginação
        PageableHandlerMethodArgumentResolver pageHandler = new PageableHandlerMethodArgumentResolver();
        // Por padrão, sempre que houver um elemento com paginação, 
        // A paginação começa na página 0 e a qtde de elementos é 20
        pageHandler.setFallbackPageable(PageRequest.of(0, 20));

        // Configurações adicionadas ao resolver:
        resolver.add(pageHandler);
    }
}
