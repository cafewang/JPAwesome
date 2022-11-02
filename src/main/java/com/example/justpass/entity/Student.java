package com.example.justpass.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    private Classroom classroom;

    public enum Gender {
        MALE, FEMALE;
    }
}
