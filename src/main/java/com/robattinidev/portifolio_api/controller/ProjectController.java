package com.robattinidev.portifolio_api.controller;

import com.robattinidev.portifolio_api.domain.Project;
import com.robattinidev.portifolio_api.dto.ProjectDTO;
import com.robattinidev.portifolio_api.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository repository;

    @PostMapping
    public ResponseEntity saveProject(@RequestBody ProjectDTO data) {
        Project newProject = new Project(data);
        this.repository.save(newProject);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projectList = this.repository.findAll();
        return ResponseEntity.ok(projectList);
    }
}