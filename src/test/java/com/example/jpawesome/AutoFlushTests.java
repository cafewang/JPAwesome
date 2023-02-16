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
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class AutoFlushTests {
    @Resource
    private StudentRepo studentRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void noTransaction() {
        Student studentTom = new Student();
        studentTom.setAge(20);
        studentTom.setName("Tom");
        studentTom = studentRepo.save(studentTom);
        studentTom.setAge(21);
        Assertions.assertThat(studentRepo.findByAge(21)).isEmpty();
    }

    @Test
    @Transactional
    @Commit
    void withinTransaction() {
        Student studentTom = new Student();
        studentTom.setAge(20);
        studentTom.setName("Tom");
        studentTom = studentRepo.save(studentTom);
        studentTom.setAge(21);
        Assertions.assertThat(studentRepo.findByAge(21)).isEmpty();
    }

    @Test
    @Transactional
    @Commit
    void manuallySetFlushMode() {
        Student studentTom = new Student();
        studentTom.setAge(20);
        studentTom.setName("Tom");
        studentTom = studentRepo.save(studentTom);
        studentTom.setAge(21);
        List<?> studentList = entityManager.createQuery("select student From Student student where student.age = 21")
                        .setFlushMode(FlushModeType.COMMIT).getResultList();
        Assertions.assertThat(studentList).isEmpty();
    }
}
