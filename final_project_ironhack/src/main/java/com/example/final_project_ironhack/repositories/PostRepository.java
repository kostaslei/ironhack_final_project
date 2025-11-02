package com.example.final_project_ironhack.repositories;

import com.example.final_project_ironhack.models.Post;
import com.example.final_project_ironhack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user); // Get all posts by a specific user
}

