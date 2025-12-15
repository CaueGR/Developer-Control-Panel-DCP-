package com.robattinidev.portifolio_api.infra.security;

import com.robattinidev.portifolio_api.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
     
        // Se a requisição for OPTIONS (Pre-flight), deixa passar sem checar token
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
    
        var token = this.recoverToken(request);
        
        if (token != null) {
            var subject = tokenService.validateToken(token);
            
            if (subject != null) {
                UserDetails user = userRepository.findByUsername(subject);

                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("✅ Autenticado com sucesso: " + subject);
                } else {
                    System.out.println("❌ Usuário não encontrado no banco: " + subject);
                }
            } else {
                System.out.println("❌ Token inválido ou expirado.");
            }
        } else {
            System.out.println("⚠️ Requisição sem token (Pode ser erro se a rota for privada).");
        }
        
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}