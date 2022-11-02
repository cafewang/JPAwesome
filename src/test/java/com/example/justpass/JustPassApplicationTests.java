package com.example.justpass;

import com.example.justpass.entity.Classroom;
import com.example.justpass.entity.Student;
import com.example.justpass.entity.Teacher;
import com.example.justpass.repo.ClassroomRepository;
import com.example.justpass.repo.TeacherRepository;
import com.example.justpass.utils.ConsoleCaptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@SpringBootTest
@ActiveProfiles("test")
class JustPassApplicationTests {
    @Resource
    private EntityManager entityManager;
    @Resource
    private TeacherRepository teacherRepository;
    @Resource
    private ClassroomRepository classroomRepository;

    @Test
    @Transactional
    void notFetchClassroomList() throws ExecutionException, InterruptedException {
        saveData();
        entityManager.clear();

        ConsoleCaptor consoleCaptor = new ConsoleCaptor();
        CompletableFuture<Boolean> resultFuture = consoleCaptor.expect(List.of("select", "from classroom"), 2000);
        Teacher teacherFound = teacherRepository.findById("老师").orElse(null);
        Assertions.assertThatThrownBy(() -> resultFuture.get()).hasRootCauseExactlyInstanceOf(TimeoutException.class)
                .as("table classroom not queried");
        CompletableFuture<Boolean> resultFuture2 = consoleCaptor.expect(List.of("select", "from student"), 2000);
        teacherFound.getClassroomList().isEmpty();
        Assertions.assertThat(resultFuture2.get()).isTrue().as("table student queried");
    }

    @Test
    void loadVsFetch() throws ExecutionException, InterruptedException {
        saveData();
        entityManager.clear();

        ConsoleCaptor consoleCaptor = new ConsoleCaptor();
        CompletableFuture<Boolean> fetchFuture = consoleCaptor.expect(List.of("select", "from teacher"), 2000);
        classroomRepository.findByNumber(1);
        Assertions.assertThatThrownBy(() -> fetchFuture.get()).hasRootCauseExactlyInstanceOf(TimeoutException.class)
                .as("teacher is not in entity graph, is ignored by fetch");

        CompletableFuture<Boolean> loadFuture = consoleCaptor.expect(List.of("select", "from teacher"), 2000);
        classroomRepository.getByNumber(2);
        Assertions.assertThat(loadFuture.get()).isTrue()
                .as("many to one is eager by default with load");
    }

    private void saveData() {
        Teacher teacher = Teacher.builder().name("老师").age(28L).build();
        Classroom room1 = new Classroom();
        room1.setNumber(1);
        room1.setTeacher(teacher);
        Classroom room2 = new Classroom();
        room2.setNumber(2);
        room2.setTeacher(teacher);
        teacher.setClassroomList(List.of(room1, room2));
        Student bob = Student.builder().name("bob").gender(Student.Gender.MALE).build();
        bob.setClassroom(room1);
        Student tom = Student.builder().name("tom").gender(Student.Gender.MALE).build();
        tom.setClassroom(room2);
        Student jennie = Student.builder().name("jennie").gender(Student.Gender.FEMALE).build();
        jennie.setClassroom(room1);
        room1.setStudentList(List.of(bob, jennie));
        room2.setStudentList(List.of(tom));
        teacherRepository.saveAndFlush(teacher);
    }
}
