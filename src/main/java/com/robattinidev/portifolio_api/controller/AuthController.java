package com.robattinidev.portifolio_api.controller;

import com.robattinidev.portifolio_api.domain.User;
import com.robattinidev.portifolio_api.dto.LoginRequestDTO;
import com.robattinidev.portifolio_api.dto.LoginResponseDTO;
import com.robattinidev.portifolio_api.infra.security.TokenService;
import com.robattinidev.portifolio_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails; // Import necessário
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO body) {
     
        UserDetails userDetails = repository.findByUsername(body.username());

       
        if (userDetails != null) {
           
            if (passwordEncoder.matches(body.password(), userDetails.getPassword())) {
                
               
                String token = tokenService.generateToken((User) userDetails);
                return ResponseEntity.ok(new LoginResponseDTO(token));
            }
        }
        
       
        return ResponseEntity.badRequest().build();
    }
}