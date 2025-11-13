package com.example.final_project_ironhack.repositories;

import com.example.final_project_ironhack.models.Application;
import com.example.final_project_ironhack.models.Project;
import com.example.final_project_ironhack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser(User user);
    List<Application> findByProject(Project project);
    Optional<Application> findByUserAndProject(User user, Project project);
}

