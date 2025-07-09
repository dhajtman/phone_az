package com.phone.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
        ).oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.decoder(jwtDecoder()))
        );

        return http.build();
    }

    @Bean
    @Lazy
    public JwtDecoder jwtDecoder() {
        try {
            log.info("Trying to initialize JwtDecoder from issuer: {}", issuerUri);
            return JwtDecoders.fromIssuerLocation(issuerUri);
        } catch (Exception e) {
            log.error("Failed to initialize JwtDecoder from issuer {}: {}", issuerUri, e.getMessage());
            return token -> {
                throw new JwtException("Keycloak is not available at: " + issuerUri);
            };
        }
    }
}
