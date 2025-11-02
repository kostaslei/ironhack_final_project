package com.example.final_project_ironhack.controllers;

import com.example.final_project_ironhack.models.Comment;
import com.example.final_project_ironhack.models.Post;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.repositories.PostRepository;
import com.example.final_project_ironhack.repositories.UserRepository;
import com.example.final_project_ironhack.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        List<Comment> comments = commentService.getCommentsByPost(post);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<Comment> createComment(@AuthenticationPrincipal Jwt jwt,
                                                 @PathVariable Long postId,
                                                 @Valid @RequestBody Comment comment) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment created = commentService.createComment(user, post, comment);
        return ResponseEntity.created(URI.create("/api/comments/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@AuthenticationPrincipal Jwt jwt,
                                                 @PathVariable Long commentId,
                                                 @Valid @RequestBody Comment comment) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment updated = commentService.updateComment(user, commentId, comment);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal Jwt jwt,
                                              @PathVariable Long commentId) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        commentService.deleteComment(user, commentId);
        return ResponseEntity.noContent().build();
    }
}

