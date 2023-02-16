package com.example.jpawesome;

import com.example.jpawesome.entity.Student;
import com.example.jpawesome.repo.StudentRepo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class UpdateTests {
    @Resource
    private StudentRepo studentRepo;

    @Test
    void updateTimeNotSet() {
        Student studentTom = new Student();
        studentTom.setAge(20);
        studentTom.setName("Tom");
        studentTom = studentRepo.save(studentTom);
        Assertions.assertThat(studentTom.getUpdateTime()).isNull();

        studentTom = studentRepo.findById(1L).orElse(null);
        Assertions.assertThat(studentTom.getUpdateTime()).isNotNull();

        String oldUpdateTime = studentTom.getUpdateTime().toString();
        studentTom.setAge(21);
        studentRepo.save(studentTom);
        studentTom = studentRepo.findById(1L).orElse(null);
        String newUpdateTime = studentTom.getUpdateTime().toString();
        log.info("{} vs {}", oldUpdateTime, newUpdateTime);
        Assertions.assertThat(oldUpdateTime).isNotEqualTo(newUpdateTime);
    }
}
