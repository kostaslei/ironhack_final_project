package com.example.final_project_ironhack.services;

import com.example.final_project_ironhack.models.Chat;
import com.example.final_project_ironhack.models.Message;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<Message> getMessagesByChat(Chat chat) {
        return messageRepository.findByChatOrderBySentAtAsc(chat);
    }

    public List<Message> getMessagesBySender(User sender) {
        return messageRepository.findBySender(sender);
    }

    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }

    public Message sendMessage(User sender, Chat chat, Message message) {
        message.setSender(sender);
        message.setChat(chat);
        return messageRepository.save(message);
    }

    public Message updateMessage(User sender, Long messageId, Message updatedMessage) {
        Message existing = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (!existing.getSender().getId().equals(sender.getId())) {
            throw new RuntimeException("You can only update your own messages");
        }

        existing.setContent(updatedMessage.getContent());
        existing.setRead(updatedMessage.isRead());

        return messageRepository.save(existing);
    }

    public void deleteMessage(User sender, Long messageId) {
        Message existing = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (!existing.getSender().getId().equals(sender.getId())) {
            throw new RuntimeException("You can only delete your own messages");
        }

        messageRepository.delete(existing);
    }
}
