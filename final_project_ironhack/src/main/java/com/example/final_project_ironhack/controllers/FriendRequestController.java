package com.example.final_project_ironhack.controllers;

import com.example.final_project_ironhack.models.FriendRequest;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.models.UserProfile;
import com.example.final_project_ironhack.repositories.UserProfileRepository;
import com.example.final_project_ironhack.repositories.UserRepository;
import com.example.final_project_ironhack.services.FriendRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestService friendRequestService;
    private final UserRepository userRepository;

    @GetMapping("/received")
    public ResponseEntity<List<FriendRequest>> getReceivedRequests(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        UserProfile userProfile = user.getProfile();
        List<FriendRequest> requests = friendRequestService.getReceivedRequests(userProfile);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/sent")
    public ResponseEntity<List<FriendRequest>> getSentRequests(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        UserProfile userProfile = user.getProfile();
        List<FriendRequest> requests = friendRequestService.getSentRequests(userProfile);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/send")
    public ResponseEntity<FriendRequest> sendRequest(@AuthenticationPrincipal Jwt jwt,
                                                     @Valid  @RequestBody UserProfile receiverProfile) {
        String email = jwt.getSubject();
        User sender = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile senderProfile = sender.getProfile();
        FriendRequest request = friendRequestService.sendRequest(senderProfile, receiverProfile);
        return ResponseEntity.created(URI.create("/api/friends/" + request.getId())).body(request);
    }

    @PutMapping("/respond/{requestId}")
    public ResponseEntity<FriendRequest> respondRequest(@AuthenticationPrincipal Jwt jwt,
                                                        @PathVariable Long requestId,
                                                        @RequestParam FriendRequest.Status status) {
        String email = jwt.getSubject();
        User receiver = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        UserProfile receiverProfile = receiver.getProfile();

        FriendRequest updated = friendRequestService.updateRequestStatus(receiverProfile, requestId, status);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteRequest(@AuthenticationPrincipal Jwt jwt,
                                              @PathVariable Long requestId) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile userProfile = user.getProfile();
        friendRequestService.deleteRequest(userProfile, requestId);
        return ResponseEntity.noContent().build();
    }
}
