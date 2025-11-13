package com.example.final_project_ironhack.repositories;

import com.example.final_project_ironhack.models.PostBase;
import com.example.final_project_ironhack.models.PostBase;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostBase, Long> {
    List<PostBase> findByUserProfile(UserProfile profile);
}

