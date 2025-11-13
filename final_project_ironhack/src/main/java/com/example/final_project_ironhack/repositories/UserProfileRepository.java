package com.example.final_project_ironhack.repositories;

import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser(User user);
    List<UserProfile> findByLocationContainingIgnoreCase(String location);
    List<UserProfile> findByJobTitleContainingIgnoreCase(String jobTitle);

    List<UserProfile> findBySkills_NameIgnoreCase(String skillName);
    @Query("SELECT u FROM UserProfile u JOIN u.skills s WHERE s.name IN :skills")
    List<UserProfile> findBySkills(@Param("skills") List<String> skills);
}

