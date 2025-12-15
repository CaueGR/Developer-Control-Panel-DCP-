package com.robattinidev.portifolio_api.infra.security;

import com.robattinidev.portifolio_api.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    
    private static final String SECRET_KEY = "MinhaChaveSecretaSuperSeguraParaOPortfolio"; 

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername()) 
                .setIssuedAt(new Date()) 
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) 
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject(); 
        } catch (Exception e) {
            return null; 
        }
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}