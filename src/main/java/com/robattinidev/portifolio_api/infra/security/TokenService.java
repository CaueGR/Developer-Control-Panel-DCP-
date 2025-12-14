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

    // Em produção, isso deve vir de uma Variável de Ambiente
    private static final String SECRET_KEY = "MinhaChaveSecretaSuperSeguraParaOPortfolio"; 

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername()) // O "dono" do token
                .setIssuedAt(new Date()) // Data de criação
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Expira em 24h
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Assinatura digital
                .compact();
    }

    public String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject(); // Retorna o email do usuário se for válido
        } catch (Exception e) {
            return null; // Token inválido ou expirado
        }
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}