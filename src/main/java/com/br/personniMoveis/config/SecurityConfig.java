package com.br.personniMoveis.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Todas requisições para API são autorizadas e validadas no filtro.
     * 
     * @param http
     * @return
     * @throws Exception 
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(
                        // Qualquer requisição para as seguintes URIs não precisa de autenticação.
                        "/", 
                        "/home", 
                        "/users/**", 
                        "/products",
                               "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html"
                )
                .permitAll()
                // Requisições para URIs diferentes das especificadas exigem 
                // autenticação e/ou autorização de um role específico 
                // falta implementar...
                .anyRequest().anonymous()
            );
//            .formLogin(formLogin -> formLogin
//                .loginPage("/login")
//                .permitAll()
//            )
//            .rememberMe(Customizer.withDefaults());
        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Configurar os domínios permitidos (ou "*", que permite todos)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = PasswordEncoderFactories.createDelegatingPasswordEncoder().
//                            .username("user")
//                            .password("password")
//                            .roles("USER")
//                            .build();
//        
//        return new InMemoryUserDetailsManager(user);
//    }
}
