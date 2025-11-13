package com.example.final_project_ironhack.repositories;

import com.example.final_project_ironhack.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByCategoryIgnoreCase(String category);
    List<Project> findByStatus(Project.Status status);
}
