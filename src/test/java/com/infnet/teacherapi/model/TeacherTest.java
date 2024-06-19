package com.infnet.teacherapi.model;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringJUnitConfig
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeacherTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should not create teacher with invalid CPF")
    void shouldNotCreateTeacherWithInvalidCpf() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String cpf = "12345678900"; // Invalid CPF
        String email = "john.doe@example.com";
        String cellphone = "999999999";
        String address = "123 Main St";
        String role = "Teacher";
        List<Discipline> disciplines = Collections.emptyList();

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Teacher().create(name, cpf, email, cellphone, address, role, disciplines);
        });
    }

    @Test
    @DisplayName("Should create teacher with valid data")
    void shouldCreateTeacherWithValidData() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "Jane Smith";
        String cpf = "12345678909"; // Valid CPF
        String email = "jane.smith@example.com";
        String cellphone = "888888888";
        String address = "456 Oak St";
        String role = "Teacher";
        List<Discipline> disciplines = Collections.singletonList(new Discipline());

        // Act
        Teacher teacher = new Teacher();
        teacher.create(name, cpf, email, cellphone, address, role, disciplines);

        // Assert
        assertNotNull(teacher);
        assertEquals(id, teacher.getId());
        assertEquals(name, teacher.getName());
        assertEquals(email, teacher.getEmail());
        assertEquals(cellphone, teacher.getCellphone());
        assertEquals(address, teacher.getAddress());
        assertEquals(role, teacher.getRole());
        assertEquals(disciplines, teacher.getDisciplines());
    }

    @Test
    @DisplayName("Should not create teacher with invalid role")
    void shouldNotCreateTeacherWithInvalidRole() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String cpf = "12345678909"; // Valid CPF
        String email = "john.doe@example.com";
        String cellphone = "999999999";
        String address = "123 Main St";
        String role = "InvalidRole"; // Invalid role
        List<Discipline> disciplines = Collections.emptyList();

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Teacher().create(name, cpf, email, cellphone, address, role, disciplines);
        });
    }

    @Test
    @DisplayName("Should not create teacher with null disciplines")
    void shouldNotCreateTeacherWithNullDisciplines() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String cpf = "12345678909"; // Valid CPF
        String email = "john.doe@example.com";
        String cellphone = "999999999";
        String address = "123 Main St";
        String role = "Teacher";

        // Act + Assert
        assertThrows(NullPointerException.class, () -> {
            new Teacher().create(name, cpf, email, cellphone, address, role, null);
        });
    }

    @Test
    @DisplayName("Should not create teacher with empty name")
    void shouldNotCreateTeacherWithEmptyName() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = ""; // Empty name
        String cpf = "12345678909"; // Valid CPF
        String email = "john.doe@example.com";
        String cellphone = "999999999";
        String address = "123 Main St";
        String role = "Teacher";
        List<Discipline> disciplines = Collections.emptyList();

        // Act + Assert
        assertThrows(ConstraintViolationException.class, () -> {
            new Teacher().create(name, cpf, email, cellphone, address, role, disciplines);
        });
    }
}
