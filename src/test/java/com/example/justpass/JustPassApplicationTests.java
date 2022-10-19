package com.example.justpass;

import com.example.justpass.entity.Student;
import com.example.justpass.repo.StudentRepository;
import org.hibernate.metamodel.spi.MetamodelImplementor;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;

@SpringBootTest
@ActiveProfiles("test")
class JustPassApplicationTests {

    @Resource
    private EntityManager entityManager;
    @Resource
    private StudentRepository studentRepository;

    @Test
    void printTableStructure() {
        EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
        MetamodelImplementor metamodelImplementor = (MetamodelImplementor) entityManagerFactory.getMetamodel();
        metamodelImplementor.entityPersisters().forEach((key, entityPersister) -> {
            SingleTableEntityPersister singleTableEntityPersister = (SingleTableEntityPersister) entityPersister;
            System.out.println(singleTableEntityPersister.getTableName());
            singleTableEntityPersister.getAttributes().forEach(attr -> {
                System.out.println(singleTableEntityPersister.getPropertyType(attr.getName()).getName());
                Arrays.stream(singleTableEntityPersister.getPropertyColumnNames(attr.getName())).forEach(System.out::println);
            });
        });
    }

    @Test
    void saveAndRead() {
        Student student = new Student();
        student.setFirstName("ha");
        student.setLastName("heng");
        studentRepository.save(student);
        System.out.println(entityManager.find(Student.class, 1L));
    }
}
