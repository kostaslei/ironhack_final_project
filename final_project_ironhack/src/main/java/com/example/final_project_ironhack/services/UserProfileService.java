package com.example.final_project_ironhack.services;

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
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id: " + id));
    }

    public UserProfile createProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    public UserProfile updateProfile(Long id, UserProfile updatedProfile) {
        UserProfile existingProfile = getProfileById(id);

        existingProfile.setName(updatedProfile.getName());
        existingProfile.setBio(updatedProfile.getBio());
        existingProfile.setLocation(updatedProfile.getLocation());
        existingProfile.setProfileImageUrl(updatedProfile.getProfileImageUrl());
        existingProfile.setSkills(updatedProfile.getSkills());
        // For posts, sent/received requests, projects, chats: update only if needed

        return userProfileRepository.save(existingProfile);
    }

    public void deleteProfile(Long id) {
        userProfileRepository.deleteById(id);
    }
}

