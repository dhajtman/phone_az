package com.phone.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.jwks-url}")
    private String jwksUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // remove "Bearer "

            try {
                String[] tokenParts = token.split("\\.");
                String headerJson = new String(Base64.getUrlDecoder().decode(tokenParts[0]));
                JsonNode header = objectMapper.readTree(headerJson);
                String kid = header.get("kid").asText();
                RSAPublicKey publicKey = fetchPublicKeyFromJwks(kid);

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(publicKey)  // must be RS256-compatible
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.get("preferred_username", String.class);
                Map<String, Object> realmAccess = claims.get("realm_access", Map.class);
                List<String> roles = (List<String>) realmAccess.get("roles");

                log.info("Authenticated user: {}, roles: {}", username, roles);
            } catch (Exception e) {
                log.error("Invalid JWT token: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private RSAPublicKey fetchPublicKeyFromJwks(String kid) throws Exception {
        JsonNode keysNode = objectMapper.readTree(new URL(jwksUrl)).get("keys");

        for (JsonNode key : keysNode) {
            if (key.get("kid").asText().equals(kid)) {
                String n = key.get("n").asText();
                String e = key.get("e").asText();

                byte[] modulusBytes = Base64.getUrlDecoder().decode(n);
                byte[] exponentBytes = Base64.getUrlDecoder().decode(e);

                BigInteger modulus = new BigInteger(1, modulusBytes);
                BigInteger exponent = new BigInteger(1, exponentBytes);

                RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                return (RSAPublicKey) keyFactory.generatePublic(keySpec);
            }
        }

        throw new Exception("Unable to find matching public key for kid: " + kid);
    }
}
