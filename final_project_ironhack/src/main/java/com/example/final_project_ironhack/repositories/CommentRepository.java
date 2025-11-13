package com.example.final_project_ironhack.repositories;

import com.example.final_project_ironhack.models.Comment;
import com.example.final_project_ironhack.models.PostBase;
import com.example.final_project_ironhack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(PostBase post);
    List<Comment> findByUser(User user);
}

