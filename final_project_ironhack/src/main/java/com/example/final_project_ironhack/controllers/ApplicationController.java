package com.example.final_project_ironhack.controllers;

import com.example.final_project_ironhack.models.Application;
import com.example.final_project_ironhack.models.Project;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.repositories.ProjectRepository;
import com.example.final_project_ironhack.repositories.UserRepository;
import com.example.final_project_ironhack.services.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @GetMapping("/me")
    public ResponseEntity<List<Application>> getMyApplications(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Application> applications = applicationService.getApplicationsByUser(user);
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/project/{projectId}")
    public ResponseEntity<Application> applyToProject(@AuthenticationPrincipal Jwt jwt,
                                                      @PathVariable Long projectId,
                                                      @Valid @RequestBody String message) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Application application = applicationService.applyToProject(user, project, message);
        return ResponseEntity.created(URI.create("/api/applications/" + application.getId()))
                .body(application);
    }

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<Application> updateStatus(@AuthenticationPrincipal Jwt jwt,
                                                    @PathVariable Long applicationId,
                                                    @RequestParam Application.Status status) {
        String email = jwt.getSubject();
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Application updated = applicationService.updateApplicationStatus(owner, applicationId, status);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@AuthenticationPrincipal Jwt jwt,
                                                  @PathVariable Long applicationId) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        applicationService.deleteApplication(user, applicationId);
        return ResponseEntity.noContent().build();
    }
}

