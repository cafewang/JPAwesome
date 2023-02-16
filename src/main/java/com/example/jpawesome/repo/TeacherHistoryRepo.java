package com.example.jpawesome.repo;

import com.example.jpawesome.entity.TeacherHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherHistoryRepo extends JpaRepository<TeacherHistory, Long> {
}
