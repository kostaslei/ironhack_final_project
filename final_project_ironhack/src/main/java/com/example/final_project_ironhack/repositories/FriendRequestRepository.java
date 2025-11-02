package com.example.final_project_ironhack.repositories;

import com.example.final_project_ironhack.models.FriendRequest;
import com.example.final_project_ironhack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    List<FriendRequest> findByReceiver(User receiver);
    List<FriendRequest> findBySender(User sender);
    Optional<FriendRequest> findBySenderAndReceiver(User sender, User receiver);
}

