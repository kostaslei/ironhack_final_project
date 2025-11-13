package com.example.final_project_ironhack.services;

import com.example.final_project_ironhack.models.FriendRequest;
import com.example.final_project_ironhack.models.User;
import com.example.final_project_ironhack.models.UserProfile;
import com.example.final_project_ironhack.repositories.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;

    public List<FriendRequest> getReceivedRequests(UserProfile userProfile) {
        return friendRequestRepository.findByReceiver(userProfile);
    }

    public List<FriendRequest> getSentRequests(UserProfile userProfile) {
        return friendRequestRepository.findBySender(userProfile);
    }

    public FriendRequest sendRequest(UserProfile sender, UserProfile receiver) {
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

    public FriendRequest updateRequestStatus(UserProfile receiver, Long requestId, FriendRequest.Status status) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        if (!request.getReceiver().getId().equals(receiver.getId())) {
            throw new RuntimeException("You can only respond to friend requests sent to you");
        }

        request.setStatus(status);
        return friendRequestRepository.save(request);
    }

    public void deleteRequest(UserProfile userProfile, Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        if (!request.getSender().getId().equals(userProfile.getId()) && !request.getReceiver().getId().equals(userProfile.getId())) {
            throw new RuntimeException("You can only delete requests involving you");
        }

        friendRequestRepository.delete(request);
    }
}
