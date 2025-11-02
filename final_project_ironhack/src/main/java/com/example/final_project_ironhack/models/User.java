package com.example.final_project_ironhack.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String role = "USER";  // e.g. ADMIN, USER, etc.
}