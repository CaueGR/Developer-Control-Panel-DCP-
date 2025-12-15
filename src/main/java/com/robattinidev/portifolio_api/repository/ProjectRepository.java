package com.robattinidev.portifolio_api.repository;

import com.robattinidev.portifolio_api.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}