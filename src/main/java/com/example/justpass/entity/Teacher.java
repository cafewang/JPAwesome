package com.example.justpass.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import java.util.List;

@NamedEntityGraph
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {
    @Id
    private String name;

    private Long age;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Classroom> classroomList;
}
