package com.example.final_project_ironhack.repositories;

import com.example.final_project_ironhack.models.Chat;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByParticipantsContains(UserProfile userProfile);
}
