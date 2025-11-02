package com.example.final_project_ironhack.services;


import com.example.final_project_ironhack.models.Chat;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.repositories.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public List<Chat> getAllChatsForUser(User user) {
        return chatRepository.findByParticipantsContains(user);
    }

    public Chat getChatById(Long id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
    }

    public Chat createChat(Set<User> participants) {
        Chat chat = new Chat();
        chat.setParticipants(participants);
        return chatRepository.save(chat);
    }

    public Chat addParticipants(Long chatId, Set<User> newParticipants) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        chat.getParticipants().addAll(newParticipants);
        return chatRepository.save(chat);
    }

    public void deleteChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        chatRepository.delete(chat);
    }
}

