package com.example.final_project_ironhack.services;

import com.example.final_project_ironhack.models.Post;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<Post> getPostsByUser(User user) {
        return postRepository.findByUser(user);
    }

    public Post createPost(User user, Post post) {
        post.setUser(user);
        return postRepository.save(post);
    }

    public Post updatePost(User user, Long postId, Post updatedPost) {
        Post existing = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only update your own posts");
        }

        existing.setContent(updatedPost.getContent());
        existing.setImageUrl(updatedPost.getImageUrl());
        existing.setLikes(updatedPost.getLikes());

        return postRepository.save(existing);
    }

    public void deletePost(User user, Long postId) {
        Post existing = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only delete your own posts");
        }

        postRepository.delete(existing);
    }
}

