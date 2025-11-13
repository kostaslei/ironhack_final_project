package com.example.final_project_ironhack.services;

import com.example.final_project_ironhack.models.Application;
import com.example.final_project_ironhack.models.Project;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.repositories.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public List<Application> getApplicationsByUser(User user) {
        return applicationRepository.findByUser(user);
    }

    public List<Application> getApplicationsByProject(Project project) {
        return applicationRepository.findByProject(project);
    }

    public Application applyToProject(User user, Project project, String message) {
        if (applicationRepository.findByUserAndProject(user, project).isPresent()) {
            throw new RuntimeException("User already applied to this project");
        }

        Application application = Application.builder()
                .user(user)
                .project(project)
                .message(message)
                .status(Application.Status.PENDING)
                .build();

        return applicationRepository.save(application);
    }

    public Application updateApplicationStatus(User owner, Long applicationId, Application.Status status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!application.getProject().getMembers().contains(owner)) {
            throw new RuntimeException("Only project members can update application status");
        }

        application.setStatus(status);
        return applicationRepository.save(application);
    }

    public void deleteApplication(User user, Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!application.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only delete your own applications");
        }

        applicationRepository.delete(application);
    }
}

