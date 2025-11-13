package com.example.final_project_ironhack.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Message content is required")
    @Size(max = 1000)
    private String content;
    private boolean isRead = false;
    private LocalDateTime sentAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "chat_id")
    @JsonBackReference("chat-messages")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonBackReference("sender-messages")
    private UserProfile sender;
}

