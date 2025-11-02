package com.example.final_project_ironhack.services;

import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.models.UserProfile;
import com.example.final_project_ironhack.repositories.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public List<UserProfile> getAllProfiles() {
        return userProfileRepository.findAll();
    }

    public UserProfile getProfileById(Long id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public List<UserProfile> getProfilesByLocation(String location) {
        return userProfileRepository.findByLocationIgnoreCase(location);
    }

    public List<UserProfile> getProfilesBySkill(String skillName) {
        return userProfileRepository.findBySkills_NameIgnoreCase(skillName);
    }

    public List<UserProfile> getProfilesByJobTitle(String jobTitle) {
        return userProfileRepository.findByJobTitleContainingIgnoreCase(jobTitle);
    }

    public List<UserProfile> getProfilesBySkills(List<String> skills) {
        return userProfileRepository.findBySkills(skills);
    }

    public UserProfile getProfileByUser(User user) {
        return userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public UserProfile createProfile(User user, UserProfile profile) {
        if (userProfileRepository.findByUser(user).isPresent()) {
            throw new RuntimeException("Profile already exists for this user");
        }
        profile.setUser(user);
        return userProfileRepository.save(profile);
    }

    public UserProfile updateProfile(User user, UserProfile updatedProfile) {
        UserProfile existing = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        existing.setName(updatedProfile.getName());
        existing.setBio(updatedProfile.getBio());
        existing.setLocation(updatedProfile.getLocation());
        existing.setProfileImageUrl(updatedProfile.getProfileImageUrl());
        existing.setJobTitle(updatedProfile.getJobTitle());

        return userProfileRepository.save(existing);
    }

    public void deleteProfile(User user) {
        UserProfile profile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        userProfileRepository.delete(profile);
    }
}

