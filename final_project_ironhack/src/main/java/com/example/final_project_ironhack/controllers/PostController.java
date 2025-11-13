package com.example.final_project_ironhack.controllers;

import com.example.final_project_ironhack.models.*;
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
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    private UserProfile getProfileFromJwt(Jwt jwt) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getProfile();
    }

    @GetMapping
    public ResponseEntity<List<PostBase>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostBase> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<List<PostBase>> getMyPosts(@AuthenticationPrincipal Jwt jwt) {
        UserProfile profile = getProfileFromJwt(jwt);
        return ResponseEntity.ok(postService.getPostsByUserProfile(profile));
    }

    @PostMapping("/text")
    public ResponseEntity<TextPost> createTextPost(@AuthenticationPrincipal Jwt jwt,
                                                   @Valid @RequestBody Map<String, String> body) {
        UserProfile profile = getProfileFromJwt(jwt);
        String content = body.get("content");
        String text = body.get("text");
        TextPost created = postService.createTextPost(profile, content, text);
        return ResponseEntity.created(URI.create("/api/posts/" + created.getId())).body(created);
    }

    @PostMapping("/image")
    public ResponseEntity<ImagePost> createImagePost(@AuthenticationPrincipal Jwt jwt,
                                                     @Valid @RequestBody Map<String, String> body) {
        UserProfile profile = getProfileFromJwt(jwt);
        String content = body.get("content");
        String imageUrl = body.get("imageUrl");
        ImagePost created = postService.createImagePost(profile, content, imageUrl);
        return ResponseEntity.created(URI.create("/api/posts/" + created.getId())).body(created);
    }

    @PutMapping("/me/{postId}")
    public ResponseEntity<PostBase> updatePost(@AuthenticationPrincipal Jwt jwt,
                                               @PathVariable Long postId,
                                               @Valid @RequestBody PostBase post) {
        UserProfile profile = getProfileFromJwt(jwt);
        PostBase updated = postService.updatePost(profile, postId, post);
        return ResponseEntity.ok(updated);
    }

    // ---- DELETE ----
    @DeleteMapping("/me/{postId}")
    public ResponseEntity<Void> deletePost(@AuthenticationPrincipal Jwt jwt,
                                           @PathVariable Long postId) {
        UserProfile profile = getProfileFromJwt(jwt);
        postService.deletePost(profile, postId);
        return ResponseEntity.noContent().build();
    }
}
