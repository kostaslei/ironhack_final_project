package com.example.final_project_ironhack.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    private String name = "New User";

    @Size(max = 200, message = "Max bio size is 200 characters")
    private String bio;

    @Size(max = 100)
    private String location;
    private String profileImageUrl;

    @Size(max = 50)
    private String jobTitle;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonBackReference
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<PostBase> posts = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    @JsonManagedReference("sent")
    private List<FriendRequest> sentRequests = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    @JsonManagedReference("received")
    private List<FriendRequest> receivedRequests = new ArrayList<>();

    @ManyToMany(mappedBy = "members")
    private Set<Project> projects = new HashSet<>();

    @ManyToMany(mappedBy = "participants")
    @JsonIgnore
    private Set<Chat> chats = new HashSet<>();

}
