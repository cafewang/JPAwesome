package com.example.jpawesome;

import com.example.jpawesome.entity.Teacher;
import com.example.jpawesome.repo.TeacherRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class EntityListenerTests {
    @Resource
    private TeacherRepo teacherRepo;

    @Test
    @Transactional
    @Commit
    void saveHistoryAndSendMessage() {
        Teacher teacher = new Teacher();
        teacher.setName("Bob");
        teacher.setAge(30);
        teacherRepo.save(teacher);
    }
}
