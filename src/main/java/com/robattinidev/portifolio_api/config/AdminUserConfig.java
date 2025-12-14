package com.robattinidev.portifolio_api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.robattinidev.portifolio_api.domain.User;
import com.robattinidev.portifolio_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AdminUserConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var userAdmin = userRepository.findByUsername("admin");

        if (userAdmin.isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456")); // Senha criptografada
            userRepository.save(admin);
            System.out.println("ADMIN USER CREATED: admin / 123456");
        }
    }
}