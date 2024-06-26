package com.strnad.rezervacnisystem.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class WebConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Ujistěte se, že URL odpovídá adrese vašeho frontendového serveru
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        // Povolené HTTP metody
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Povolené hlavičky
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        // Povolit přihlašovací údaje (cookies, authorization headers)
        configuration.setAllowCredentials(true);
        // Nastavit dobu platnosti pro CORS preflight requesty
        configuration.setMaxAge(3600L);  // 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Nastavit CORS konfiguraci pro všechny cesty
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        // Nastavit vysokou prioritu pro CORS filter
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
