package com.example.justpass;

import com.alibaba.fastjson.JSONObject;
import com.example.justpass.entity.Classroom;
import com.example.justpass.entity.Student;
import com.example.justpass.entity.Teacher;
import com.example.justpass.repo.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class JustPassApplicationTests {
    @Resource
    private EntityManager entityManager;
    @Resource
    private TeacherRepository teacherRepository;

    @Test
    @Transactional
    void insert() {
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
        entityManager.clear();

        Teacher teacherFound = teacherRepository.findById("老师").orElse(null);
        System.out.println(JSONObject.toJSONString(teacherFound.getClassroomList()));
    }
}
