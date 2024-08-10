package com.hab.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final String[] freeResourceUrl = {"/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
            "/swagger-resources/**", "/api-docs/**", "/aggregate/**"};
    private final String frontendUrl = "http://localhost:4200";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(freeResourceUrl).permitAll()
                .anyRequest().authenticated()
        )
        .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(token -> token.jwtAuthenticationConverter(
                        new KeyCloakAuthenticationConverter()))
        );

        return http.build();
    }
}
