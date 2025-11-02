package com.example.final_project_ironhack.services;

import com.example.final_project_ironhack.models.Skill;
import com.example.final_project_ironhack.repositories.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill getSkillById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
    }

    public Skill getSkillByName(String name) {
        return skillRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
    }

    public Skill createSkill(Skill skill) {
        if (skillRepository.findByNameIgnoreCase(skill.getName()).isPresent()) {
            throw new RuntimeException("Skill already exists");
        }
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, Skill updatedSkill) {
        Skill existing = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        existing.setName(updatedSkill.getName());
        return skillRepository.save(existing);
    }

    public void deleteSkill(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        skillRepository.delete(skill);
    }
}

