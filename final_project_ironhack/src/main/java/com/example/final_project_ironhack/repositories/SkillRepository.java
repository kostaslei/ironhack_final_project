package com.example.final_project_ironhack.repositories;

import com.example.final_project_ironhack.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findByNameIgnoreCase(String name);
}
