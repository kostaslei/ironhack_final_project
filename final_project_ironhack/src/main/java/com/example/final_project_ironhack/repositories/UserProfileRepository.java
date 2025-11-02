package com.example.final_project_ironhack.repositories;

import com.example.final_project_ironhack.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    // You can add custom queries if needed later
}

