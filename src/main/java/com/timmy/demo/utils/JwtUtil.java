package com.timmy.demo.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import java.security.Key;


@Component
public class JwtUtil {

    private final String SECRET_KEY = "ylUSH5D7VADWezFY3OjkiAigyq6MEEEe";
    private final int EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    // Generate a JWT token
    public String generateToken(String username) {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 10 hours
                .signWith(key)
                .compact();
    }

    // Extract the username from the token
    public String extractUsername(String token) {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate the token
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        Date expirationDate = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }
}
