package com.example.final_project_ironhack.controllers;

import com.example.final_project_ironhack.models.Chat;
import com.example.final_project_ironhack.models.Message;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.models.UserProfile;
import com.example.final_project_ironhack.repositories.ChatRepository;
import com.example.final_project_ironhack.repositories.UserRepository;
import com.example.final_project_ironhack.services.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessagesByChat(@PathVariable Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        List<Message> messages = messageService.getMessagesByChat(chat);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/chat/{chatId}")
    public ResponseEntity<Message> sendMessage(@AuthenticationPrincipal Jwt jwt,
                                               @PathVariable Long chatId,
                                               @Valid @RequestBody Message message) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile senderProfile = user.getProfile();

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        Message sentMessage = messageService.sendMessage(senderProfile, chat, message);
        return ResponseEntity.created(URI.create("/api/messages/" + sentMessage.getId()))
                .body(sentMessage);
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<Message> updateMessage(@AuthenticationPrincipal Jwt jwt,
                                                 @PathVariable Long messageId,
                                                 @Valid @RequestBody Message message) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile senderProfile = user.getProfile();
        Message updated = messageService.updateMessage(senderProfile, messageId, message);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@AuthenticationPrincipal Jwt jwt,
                                              @PathVariable Long messageId) {
        String email = jwt.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile senderProfile = user.getProfile(); // ✅ get profile
        messageService.deleteMessage(senderProfile, messageId); // ✅ Pass UserProfile
        return ResponseEntity.noContent().build();
    }
}
