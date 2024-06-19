package com.infnet.teacherapi.model;

import com.infnet.teacherapi.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
@SpringJUnitConfig
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DisplayName("Should not create student with invalid CPF")
    void shouldNotCreateStudentWithInvalidCpf() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "Luiz Coser";
        String cpf = "12345678900"; // Invalid CPF
        String email = "luiz.coser@infnet.com";
        String cellphone = "999999999";
        String address = "Rio grande do sul, 123";
        String role = "Student";
        Course course = new Course();
        List<Discipline> disciplines = Collections.emptyList();

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Student().create(name, cpf, email, cellphone, address, role, course, disciplines, Collections.emptyList());
        });
    }

    @Test
    @DisplayName("Should create student with valid data")
    void shouldCreateStudentWithValidData() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "Luiz Coser";
        String cpf = "12345678909"; // Valid CPF
        String email = "luiz.coser@infnet.com";
        String cellphone = "888888888";
        String address = "Rio Grande do sul, 123";
        String role = "Student";
        Course course = new Course();
        List<Discipline> disciplines = Collections.singletonList(new Discipline());

        // Act
        Student student = new Student();
        student.create(name, cpf, email, cellphone, address, role, course, disciplines, Collections.emptyList());

        // Assert
        assertNotNull(student);
        assertEquals(id, student.getId());
        assertEquals(name, student.getName());
        assertEquals(email, student.getEmail());
        assertEquals(cellphone, student.getCellphone());
        assertEquals(address, student.getAddress());
        assertEquals(role, student.getRole());
        assertEquals(course, student.getCourse());
        assertEquals(disciplines, student.getDisciplines());
    }

    @Test
    @DisplayName("Should not create student with invalid role")
    void shouldNotCreateStudentWithInvalidRole() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "Luiz Coser";
        String cpf = "12345678909"; // Valid CPF
        String email = "luiz.coser@infnet.com";
        String cellphone = "999999999";
        String address = "Rio Grande do sul, 123";
        String role = "InvalidRole"; // Invalid role
        Course course = new Course();
        List<Discipline> disciplines = Collections.emptyList();

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Student().create(name, cpf, email, cellphone, address, role, course, disciplines, Collections.emptyList());
        });
    }

    @Test
    @DisplayName("Should not create student with null course")
    void shouldNotCreateStudentWithNullCourse() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "Luiz Coser";
        String cpf = "12345678909"; // Valid CPF
        String email = "luiz.coser@infnet.com";
        String cellphone = "999999999";
        String address = "123 Main St";
        String role = "Student";
        List<Discipline> disciplines = Collections.emptyList();

        // Act + Assert
        assertThrows(NullPointerException.class, () -> {
            new Student().create(name, cpf, email, cellphone, address, role, null, disciplines, Collections.emptyList());
        });
    }

    @Test
    @DisplayName("Should not create student with empty name")
    void shouldNotCreateStudentWithEmptyName() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = ""; // Empty name
        String cpf = "12345678909"; // Valid CPF
        String email = "luiz.coser@infnet.com";
        String cellphone = "999999999";
        String address = "123 Main St";
        String role = "Student";
        Course course = new Course();
        List<Discipline> disciplines = Collections.emptyList();

        // Act + Assert
        assertThrows(ConstraintViolationException.class, () -> {
            new Student().create(name, cpf, email, cellphone, address, role, course, disciplines, Collections.emptyList());
        });
    }
}
