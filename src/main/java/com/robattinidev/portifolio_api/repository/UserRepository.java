package com.robattinidev.portifolio_api.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.robattinidev.portifolio_api.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
   
    UserDetails findByUsername(String username);
}