package com.example.jpawesome;

import com.example.jpawesome.entity.Student;
import com.example.jpawesome.repo.StudentRepo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class UpdateTests {
    @Resource
    private StudentRepo studentRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    @Commit
    void updateTimeNotSet() {
        Student studentTom = new Student();
        studentTom.setAge(20);
        studentTom.setName("Tom");
        studentTom = studentRepo.save(studentTom);
        entityManager.refresh(studentTom);
        Assertions.assertThat(studentTom.getUpdateTime()).isNotNull();

        String oldUpdateTime = studentTom.getUpdateTime().toString();
        studentTom.setAge(21);
        studentTom = studentRepo.saveAndFlush(studentTom);
        entityManager.refresh(studentTom);
        String newUpdateTime = studentTom.getUpdateTime().toString();
        log.info("{} vs {}", oldUpdateTime, newUpdateTime);
        Assertions.assertThat(oldUpdateTime).isNotEqualTo(newUpdateTime);
    }
}
