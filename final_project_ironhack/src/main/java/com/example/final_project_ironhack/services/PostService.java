package com.example.final_project_ironhack.services;

import com.example.final_project_ironhack.models.*;
import com.example.final_project_ironhack.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostBase> getAllPosts() {
        return postRepository.findAll();
    }

    public PostBase getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<PostBase> getPostsByUserProfile(UserProfile userProfile) {
        return postRepository.findByUserProfile(userProfile);
    }

    public TextPost createTextPost(UserProfile userProfile, String content, String text) {
        TextPost post = new TextPost();
        post.setUserProfile(userProfile);
        post.setContent(content);
        post.setText(text);
        return postRepository.save(post);
    }

    public ImagePost createImagePost(UserProfile userProfile, String content, String imageUrl) {
        ImagePost post = new ImagePost();
        post.setUserProfile(userProfile);
        post.setContent(content);
        post.setImageUrl(imageUrl);
        return postRepository.save(post);
    }

    public PostBase updatePost(UserProfile userProfile, Long postId, PostBase updatedPost) {
        PostBase existing = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!existing.getUserProfile().getId().equals(userProfile.getId())) {
            throw new RuntimeException("You can only update your own posts");
        }

        existing.setContent(updatedPost.getContent());

        if (existing instanceof TextPost textPost && updatedPost instanceof TextPost updatedText) {
            textPost.setText(updatedText.getText());
        } else if (existing instanceof ImagePost imagePost && updatedPost instanceof ImagePost updatedImage) {
            imagePost.setImageUrl(updatedImage.getImageUrl());
        }

        return postRepository.save(existing);
    }

    public void deletePost(UserProfile userProfile, Long postId) {
        PostBase existing = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!existing.getUserProfile().getId().equals(userProfile.getId())) {
            throw new RuntimeException("You can only delete your own posts");
        }

        postRepository.delete(existing);
    }
}
