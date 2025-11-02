package com.example.final_project_ironhack.controllers;

import com.example.final_project_ironhack.models.Chat;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.repositories.UserRepository;
import com.example.final_project_ironhack.services.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<List<Chat>> getMyChats(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Chat> chats = chatService.getAllChatsForUser(user);
        return ResponseEntity.ok(chats);
    }

    @PostMapping
    public ResponseEntity<Chat> createChat(@AuthenticationPrincipal Jwt jwt,
                                           @Valid @RequestBody Set<Long> participantIds) {
        String email = jwt.getSubject();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<User> participants = new HashSet<>();
        participants.add(currentUser);

        for (Long id : participantIds) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found: " + id));
            participants.add(user);
        }

        Chat chat = chatService.createChat(participants);
        return ResponseEntity.created(URI.create("/api/chats/" + chat.getId())).body(chat);
    }

    @PutMapping("/{chatId}/add-participants")
    public ResponseEntity<Chat> addParticipants(@AuthenticationPrincipal Jwt jwt,
                                                @PathVariable Long chatId,
                                                @Valid @RequestBody Set<Long> participantIds) {

        Set<User> newParticipants = new HashSet<>();
        for (Long id : participantIds) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found: " + id));
            newParticipants.add(user);
        }

        Chat updated = chatService.addParticipants(chatId, newParticipants);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.noContent().build();
    }
}
