package com.example.final_project_ironhack.repositories;

import com.example.final_project_ironhack.models.Chat;
import com.example.final_project_ironhack.models.Message;
import com.example.final_project_ironhack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChatOrderBySentAtAsc(Chat chat);
    List<Message> findBySender(User sender);
}

