package com.example.final_project_ironhack.controllers;

import com.example.final_project_ironhack.models.Post;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.repositories.UserRepository;
import com.example.final_project_ironhack.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<List<Post>> getMyPosts(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(postService.getPostsByUser(user));
    }

    @PostMapping("/me")
    public ResponseEntity<Post> createPost(@AuthenticationPrincipal Jwt jwt,
                                           @Valid @RequestBody Post post) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post created = postService.createPost(user, post);
        return ResponseEntity.created(URI.create("/api/posts/" + created.getId())).body(created);
    }

    @PutMapping("/me/{postId}")
    public ResponseEntity<Post> updatePost(@AuthenticationPrincipal Jwt jwt,
                                           @PathVariable Long postId,
                                           @Valid @RequestBody Post post) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post updated = postService.updatePost(user, postId, post);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/me/{postId}")
    public ResponseEntity<Void> deletePost(@AuthenticationPrincipal Jwt jwt,
                                           @PathVariable Long postId) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        postService.deletePost(user, postId);
        return ResponseEntity.noContent().build();
    }
}

