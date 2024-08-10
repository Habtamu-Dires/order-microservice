package com.hab.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;


@Configuration
public class BeansConfig { //implements WebMvcConfigurer {

    @Configuration
    public class CorsConfig {

        @Bean
        public CorsFilter corsFilter()
        {
            CorsConfiguration corsConfig = new CorsConfiguration();
            corsConfig.setAllowCredentials(true);
            corsConfig.setAllowedOrigins(List.of("http://localhost:4200"));
            corsConfig.setAllowedHeaders(Arrays.asList("*"));
            corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Include OPTIONS for pre-flight

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", corsConfig);

            return
                    new CorsFilter(source);
        }
    }

}
