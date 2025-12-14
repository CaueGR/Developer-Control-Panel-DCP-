package com.robattinidev.portifolio_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robattinidev.portifolio_api.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}