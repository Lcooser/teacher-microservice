package com.infnet.teacherapi.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldNotCreateUserWithoutName() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setCellphone("1234567890");
        user.setAddress("123 Main St");
        user.setRole("User");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("The field name can not be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    void shouldNotCreateUserWithoutEmail() {
        User user = new User();
        user.setName("John Doe");
        user.setCellphone("1234567890");
        user.setAddress("123 Main St");
        user.setRole("User");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("The field email can not be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    void shouldNotCreateUserWithoutCellphone() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("test@example.com");
        user.setAddress("123 Main St");
        user.setRole("User");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("The field cellphone can not be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    void shouldNotCreateUserWithoutAddress() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("test@example.com");
        user.setCellphone("1234567890");
        user.setRole("User");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("The field address can not be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    void shouldNotCreateUserWithoutRole() {
        User user = new User();
        user.create( "XPTO", "0259419014",  "joao.lima@puc.edu.br", "99124956", "São goncalvez", "Teacher");
        user.setName("XPTO");
        user.setEmail("joao.lima@puc.edu.br");
        user.setCellphone("99124956");
        user.setAddress("São goncalvez");
        user.setRole("Teacher");


        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("The field role can not be null or empty", violations.iterator().next().getMessage());
    }

    @Test
    void shouldCreateValidUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("test@example.com");
        user.setCellphone("1234567890");
        user.setAddress("123 Main St");
        user.setRole("User");
        ;
    }
}
