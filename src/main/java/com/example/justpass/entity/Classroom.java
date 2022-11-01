package com.example.justpass.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import java.util.List;

@NamedEntityGraph(
        attributeNodes = {@NamedAttributeNode("studentList")}
)
@Entity
@Getter
@Setter
public class Classroom {
    @Id
    private Integer number;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classroom")
    private List<Student> studentList;

    @ManyToOne
    private Teacher teacher;
}
