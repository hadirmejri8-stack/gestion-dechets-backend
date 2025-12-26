package org.municipalite.gestiondechets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Autorise Next.js (localhost:3000)
        config.addAllowedOrigin("http://localhost:3000");

        // Autorise toutes les méthodes HTTP
        config.addAllowedMethod("*");

        // Autorise tous les headers
        config.addAllowedHeader("*");

        // Autorise les cookies/credentials
        config.setAllowCredentials(true);

        // Enregistre cette configuration pour toutes les routes
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}