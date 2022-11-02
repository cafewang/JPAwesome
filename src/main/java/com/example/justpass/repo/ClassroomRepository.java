package com.example.justpass.repo;

import com.example.justpass.entity.Classroom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    @EntityGraph(attributePaths = {"number"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Classroom> findByNumber(Integer number);

    @EntityGraph(attributePaths = {"number"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Classroom> getByNumber(Integer number);
}
