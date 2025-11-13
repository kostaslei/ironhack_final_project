package com.example.final_project_ironhack.services;

import com.example.final_project_ironhack.models.Project;
import com.example.final_project_ironhack.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public List<Project> getProjectsByCategory(String category) {
        return projectRepository.findByCategoryIgnoreCase(category);
    }

    public List<Project> getProjectsByStatus(Project.Status status) {
        return projectRepository.findByStatus(status);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project updatedProject) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        existing.setTitle(updatedProject.getTitle());
        existing.setDescription(updatedProject.getDescription());
        existing.setCategory(updatedProject.getCategory());
        existing.setStatus(updatedProject.getStatus());
        existing.setMembers(updatedProject.getMembers());
        existing.setRequiredSkills(updatedProject.getRequiredSkills());

        return projectRepository.save(existing);
    }

    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        projectRepository.delete(project);
    }
}

