package com.example.jpawesome.listener;

import com.example.jpawesome.entity.Teacher;
import com.example.jpawesome.entity.TeacherHistory;
import com.example.jpawesome.repo.TeacherHistoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import java.time.Instant;

@Slf4j
@Component
public class TeacherHistoryListener {
    private static TeacherHistoryRepo teacherHistoryRepo;

    @Autowired
    public void setTeacherHistoryRepo(TeacherHistoryRepo teacherHistoryRepo) {
        TeacherHistoryListener.teacherHistoryRepo = teacherHistoryRepo;
    }

    @PostPersist
    public void postPersist(Teacher teacher) {
        TeacherHistory teacherHistory = new TeacherHistory();
        teacherHistory.setTeacherId(teacher.getId());
        teacherHistory.setName(teacher.getName());
        teacherHistory.setAge(teacher.getAge());
        teacherHistory.setUpdateTime(Instant.now());
        log.info("teacher history saved: {}", teacherHistoryRepo.save(teacherHistory));
    }
}
