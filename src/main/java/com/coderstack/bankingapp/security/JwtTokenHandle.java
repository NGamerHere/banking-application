package com.coderstack.bankingapp.security;

import java.security.Key;
import java.util.Date;
import java.util.Map;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenHandle { 
    public static final long TOKEN_VALIDITY = 10 * 60 * 60 * 1000;

    final private Key signingKey;

    @Autowired
    public JwtTokenHandle(@Value("${jwt.secret}") String key){
        if (key == null || key.isBlank()) {
            throw new IllegalStateException("JWT secret key is missing. Check application.properties!");
        }
        this.signingKey = decodeSecretKey(key);
    }


    public String generateToken(Map<String, Object> userData) {
        return Jwts.builder()
                .setClaims(userData)  
                .setIssuedAt(new Date()) 
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY)) 
                .signWith(signingKey, SignatureAlgorithm.HS256) 
                .compact();
    }


    public Claims getTokenData(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey) 
                .build()
                .parseClaimsJws(token)
                .getBody(); 
    }


    public boolean validateToken(String token, String username) {
        final String extractedUsername = getTokenData(token).get("username", String.class);
        return (extractedUsername != null && extractedUsername.equals(username) && !isTokenExpired(token));
    }


    public boolean isTokenExpired(String token) {
        return getTokenData(token).getExpiration().before(new Date());
    }


    private Key decodeSecretKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
