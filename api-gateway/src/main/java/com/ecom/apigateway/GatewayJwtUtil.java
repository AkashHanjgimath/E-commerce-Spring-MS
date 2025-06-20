package com.ecom.apigateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class GatewayJwtUtil {

    private static final String SECRET = "aCiRVM+k6wIxcJSx3+zGii8wsn6P6SV7P1tfTUQipjs=";

    // Use jjwt 0.11.5-compatible key
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    //  Validates the JWT token
    public boolean validateToken(String token) {
        try {
            extractClaims(token); // If parsing fails, exception is thrown
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extracts username (subject) from token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Parses and extracts all claims
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
