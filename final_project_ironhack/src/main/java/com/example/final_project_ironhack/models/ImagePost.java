package com.example.final_project_ironhack.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("IMAGE")
public class ImagePost extends PostBase {
    private String imageUrl;
}
