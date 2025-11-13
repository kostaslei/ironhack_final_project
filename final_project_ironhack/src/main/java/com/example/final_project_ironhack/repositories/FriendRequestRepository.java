package com.example.final_project_ironhack.repositories;

import com.example.final_project_ironhack.models.FriendRequest;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    List<FriendRequest> findByReceiver(UserProfile receiver);
    List<FriendRequest> findBySender(UserProfile sender);
    Optional<FriendRequest> findBySenderAndReceiver(UserProfile sender, UserProfile receiver);
}

