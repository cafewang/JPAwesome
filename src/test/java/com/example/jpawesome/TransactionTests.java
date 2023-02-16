package com.example.jpawesome;

import com.example.jpawesome.entity.Student;
import com.example.jpawesome.repo.StudentRepo;
import lombok.extern.slf4j.Slf4j;
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
class TransactionTests {
    @Resource
    private StudentRepo studentRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void save_entity_outside_transaction() {
        Student studentTom = new Student();
        studentTom.setAge(20);
        studentTom.setName("Tom");
        studentTom = studentRepo.save(studentTom);
        log.info("entity managed: {}", entityManager.contains(studentTom));
        studentTom.setAge(21);
        studentRepo.save(studentTom);
    }

    @Test
    @Transactional
    @Commit
    void save_entity_within_transaction() {
        Student studentTom = new Student();
        studentTom.setAge(20);
        studentTom.setName("Tom");
        studentTom = studentRepo.save(studentTom);
        log.info("entity managed: {}", entityManager.contains(studentTom));
        studentTom.setAge(21);
    }
}
