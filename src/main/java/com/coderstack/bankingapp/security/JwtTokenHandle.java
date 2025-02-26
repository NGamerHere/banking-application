package com.coderstack.bankingapp.security;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenHandle { 
    public static final long TOKEN_VALIDITY = 10 * 60 * 60 * 1000; // 10 hours in milliseconds

    @Value("${jwt.secret}") 
    private String jwtSecret;  // Secret key from application.properties

    private Key signingKey;  // Store decoded key to avoid redundant decoding

    @PostConstruct
    public void init() {
        if (jwtSecret == null || jwtSecret.isBlank()) {
            throw new IllegalStateException("JWT secret key is missing. Check application.properties!");
        }
        this.signingKey = decodeSecretKey(jwtSecret);
    }

    // ✅ Generate JWT Token
    public String generateToken(Map<String, Object> userData) {
        return Jwts.builder()
                .setClaims(userData)  
                .setIssuedAt(new Date()) 
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY)) 
                .signWith(signingKey, SignatureAlgorithm.HS256) 
                .compact();
    }

    // ✅ Retrieve Data from JWT Token
    public Claims getTokenData(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey) 
                .build()
                .parseClaimsJws(token)
                .getBody(); 
    }

    // ✅ Validate JWT Token
    public boolean validateToken(String token, String username) {
        final String extractedUsername = getTokenData(token).get("username", String.class);
        return (extractedUsername != null && extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // ✅ Check Token Expiry
    private boolean isTokenExpired(String token) {
        return getTokenData(token).getExpiration().before(new Date());
    }

    // ✅ Decode Secret Key Once
    private Key decodeSecretKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
