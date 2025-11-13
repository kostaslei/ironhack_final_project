package com.example.final_project_ironhack.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("TEXT")
public class TextPost extends PostBase {
    private String text;
}
