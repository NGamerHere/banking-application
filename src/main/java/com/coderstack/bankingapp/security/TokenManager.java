package com.coderstack.bankingapp.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenManager {
    public static final long TOKEN_VALIDITY = 10 * 60 * 60 * 1000; // 10 hours in milliseconds

    @Value("${jwt.secret}") 
    private String jwtSecret; // Load the secret key from application.properties

    // ✅ Generate JWT Token
    public String generateToken(Map<String, Object> userData) {
        return Jwts.builder()
                .setClaims(userData)  // Add user details
                .setIssuedAt(new Date()) // Token creation time
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY)) // Expiry time
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Sign the token
                .compact();
    }

    // ✅ Retrieve Data from JWT Token
    public Claims getTokenData(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()) // Set secret key
                .build()
                .parseClaimsJws(token) // Parse token
                .getBody(); // Get payload
    }

    // ✅ Validate JWT Token
    public boolean validateToken(String token, String username) {
        final String extractedUsername = getTokenData(token).get("username", String.class);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // ✅ Check Token Expiry
    private boolean isTokenExpired(String token) {
        return getTokenData(token).getExpiration().before(new Date());
    }

    // ✅ Convert secret key from Base64
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
