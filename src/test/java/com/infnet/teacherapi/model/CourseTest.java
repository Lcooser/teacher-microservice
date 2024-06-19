package com.infnet.teacherapi.model;

import com.infnet.teacherapi.repository.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void testCreateCourseWithValidData() {
        // Arrange
        Course course = createValidCourse();

        // Act
        Course savedCourse = entityManager.persistAndFlush(course);

        // Assert
        Assertions.assertNotNull(savedCourse.getId());
        Assertions.assertEquals(course.getName(), savedCourse.getName());
    }

    @Test
    public void testCreateCourseWithNullName() {
        // Arrange
        Course course = createValidCourse();
        course.setName(null);

        // Act + Assert
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(course);
        });
    }

    @Test
    public void testCreateCourseWithShortName() {
        // Arrange
        Course course = createValidCourse();
        course.setName("C"); // Less than two characters

        // Act + Assert
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(course);
        });
    }

    @Test
    public void testCreateCourseWithLongName() {
        // Arrange
        Course course = createValidCourse();
        course.setName("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam elementum odio non est rutrum."); // More than sixty characters

        // Act + Assert
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(course);
        });
    }

    private Course createValidCourse() {
        Course course = new Course();
        course.setName("Computer Science");
        course.setStudents(Collections.emptyList());
        course.setDisciplines(Collections.emptyList());
        return course;
    }
}
