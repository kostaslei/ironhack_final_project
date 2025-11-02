package com.example.final_project_ironhack.services;

import com.example.final_project_ironhack.models.Comment;
import com.example.final_project_ironhack.models.Post;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    public List<Comment> getCommentsByUser(User user) {
        return commentRepository.findByUser(user);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public Comment createComment(User user, Post post, Comment comment) {
        comment.setUser(user);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public Comment updateComment(User user, Long commentId, Comment updatedComment) {
        Comment existing = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only update your own comments");
        }

        existing.setContent(updatedComment.getContent());
        return commentRepository.save(existing);
    }

    public void deleteComment(User user, Long commentId) {
        Comment existing = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only delete your own comments");
        }

        commentRepository.delete(existing);
    }
}

