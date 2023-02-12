package com.example.jpawesome;

import com.example.jpawesome.entity.Student;
import com.example.jpawesome.repo.StudentRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@ActiveProfiles("test")
class ApplicationTests {
    @Resource
    private StudentRepo studentRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void duplicate_key() {
        Student student = new Student();
        student.setAge(20);
        student.setName("Tom");
        Student studentTom = studentRepo.save(student);
        System.out.println(entityManager.contains(studentTom));
        studentTom.setAge(21);
        studentRepo.save(studentTom);
    }
}
