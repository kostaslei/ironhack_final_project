package com.example.final_project_ironhack.services;

import com.example.final_project_ironhack.models.FriendRequest;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.repositories.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;

    public List<FriendRequest> getReceivedRequests(User user) {
        return friendRequestRepository.findByReceiver(user);
    }

    public List<FriendRequest> getSentRequests(User user) {
        return friendRequestRepository.findBySender(user);
    }

    public FriendRequest sendRequest(User sender, User receiver) {
        if (friendRequestRepository.findBySenderAndReceiver(sender, receiver).isPresent()) {
            throw new RuntimeException("Friend request already exists");
        }

        FriendRequest request = FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .status(FriendRequest.Status.PENDING)
                .build();

        return friendRequestRepository.save(request);
    }

    public FriendRequest updateRequestStatus(User receiver, Long requestId, FriendRequest.Status status) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        if (!request.getReceiver().getId().equals(receiver.getId())) {
            throw new RuntimeException("You can only respond to friend requests sent to you");
        }

        request.setStatus(status);
        return friendRequestRepository.save(request);
    }

    public void deleteRequest(User user, Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        if (!request.getSender().getId().equals(user.getId()) && !request.getReceiver().getId().equals(user.getId())) {
            throw new RuntimeException("You can only delete requests involving you");
        }

        friendRequestRepository.delete(request);
    }
}
