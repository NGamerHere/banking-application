package com.coderstack.bankingapp.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class TokenManager {
    public static final long TOKEN_VALIDITY = 10 * 60 * 60;

    @Value("${secret}") 
   private String jwtSecret;

   private Integer UserID;
   
   public TokenManager(Integer UserID){
         this.UserID=UserID;
   }
      
    
}

