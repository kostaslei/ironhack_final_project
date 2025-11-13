package com.example.final_project_ironhack.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Skill name is required")
    @Size(max = 50)
    private String name;

    @ManyToMany(mappedBy = "skills")
    @Builder.Default
    private Set<UserProfile> users = new HashSet<>();

    @ManyToMany(mappedBy = "requiredSkills")
    @Builder.Default
    private Set<Project> projects = new HashSet<>();
}
