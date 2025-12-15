package com.robattinidev.portifolio_api.domain;

import com.robattinidev.portifolio_api.dto.ProjectDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    
    @Column(length = 1000)
    private String description;

 
    public Project(ProjectDTO data) {
        this.title = data.title();
        this.category = data.category();
        this.description = data.description();
    }
}