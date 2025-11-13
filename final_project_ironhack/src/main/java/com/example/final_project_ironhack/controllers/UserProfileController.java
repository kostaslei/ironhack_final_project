package com.example.final_project_ironhack.controllers;

import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.models.UserProfile;
import com.example.final_project_ironhack.repositories.UserRepository;
import com.example.final_project_ironhack.services.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user-profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserRepository userRepository;
    private final UserProfileService userProfileService;

    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllProfiles() {
        List<UserProfile> profiles = userProfileService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getProfileById(@PathVariable Long id) {
        UserProfile profile = userProfileService.getProfileById(id);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<UserProfile>> getProfilesByLocation(@PathVariable String location) {
        List<UserProfile> profiles = userProfileService.getProfilesByLocation(location);
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/skill/{skillName}")
    public ResponseEntity<List<UserProfile>> getProfilesBySkill(@PathVariable String skillName) {
        List<UserProfile> profiles = userProfileService.getProfilesBySkill(skillName);
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/search/skills")
    public ResponseEntity<List<UserProfile>> getProfilesBySkills(@RequestParam List<String> skills) {
        return ResponseEntity.ok(userProfileService.getProfilesBySkills(skills));
    }

    @GetMapping("/job/{jobTitle}")
    public ResponseEntity<List<UserProfile>> getProfilesByJobTitle(@PathVariable String jobTitle) {
        List<UserProfile> profiles = userProfileService.getProfilesByJobTitle(jobTitle);
        return ResponseEntity.ok(profiles);
    }

    // Authenticated Endpoints

    @GetMapping("/me")
    public ResponseEntity<UserProfile> getMyProfile(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserProfile profile = userProfileService.getProfileByUser(user);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/me")
    public ResponseEntity<UserProfile> createMyProfile(@AuthenticationPrincipal Jwt jwt,
                                                       @Valid @RequestBody UserProfile profile) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserProfile created = userProfileService.createProfile(user, profile);
        return ResponseEntity
                .created(URI.create("/api/profile/me"))
                .body(created);
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfile> updateMyProfile(@AuthenticationPrincipal Jwt jwt,
                                                       @Valid @RequestBody UserProfile profile) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserProfile updated = userProfileService.updateProfile(user, profile);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyProfile(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userProfileService.deleteProfile(user);
        return ResponseEntity.noContent().build();
    }
}

