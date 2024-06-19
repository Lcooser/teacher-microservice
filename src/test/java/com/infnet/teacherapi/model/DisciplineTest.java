package com.infnet.teacherapi.model;

import com.infnet.teacherapi.repository.DisciplineRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

@SpringJUnitConfig
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DisciplineTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Test
    public void shouldCreateDisciplineWithValidData() {
        // Arrange
        Discipline discipline = createValidDiscipline();

        // Act
        entityManager.persistAndFlush(discipline);

        // Assert
        Discipline savedDiscipline = entityManager.find(Discipline.class, discipline.getId());
        Assertions.assertNotNull(savedDiscipline);
        Assertions.assertEquals(discipline.getName(), savedDiscipline.getName());
        Assertions.assertEquals(discipline.getCode(), savedDiscipline.getCode());
        Assertions.assertEquals(discipline.getTeacher(), savedDiscipline.getTeacher());
    }

    @Test
    public void shouldNotCreateDisciplineWithoutName() {
        // Arrange
        Discipline discipline = createValidDiscipline();
        discipline.setName(null);

        // Act + Assert
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(discipline);
        });
    }

    @Test
    public void shouldNotCreateDisciplineWithoutCode() {
        // Arrange
        Discipline discipline = createValidDiscipline();
        discipline.setCode(null);

        // Act + Assert
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(discipline);
        });
    }

    @Test
    public void shouldNotCreateDisciplineWithNameLessThanTwoCharacters() {
        // Arrange
        Discipline discipline = createValidDiscipline();
        discipline.setName("M"); // Less than two characters

        // Act + Assert
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(discipline);
        });
    }

    @Test
    public void shouldNotCreateDisciplineWithNameMoreThanSixtyCharacters() {
        // Arrange
        Discipline discipline = createValidDiscipline();
        discipline.setName("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam elementum odio non est rutrum."); // More than sixty characters

        // Act + Assert
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(discipline);
        });
    }

    private Discipline createValidDiscipline() {
        Discipline discipline = new Discipline();
        discipline.setName("Mathematics");
        discipline.setCode("MAT101");

        Teacher teacher = new Teacher();
        teacher.setName("Carlos Pivatto");
        teacher.setEmail("carlos.pivatto@infnet.com");
        teacher.setCellphone("123456789");
        teacher.setAddress("Rio de Janeiro, Copacabana, 123");
        teacher.setRole("Teacher");

        entityManager.persist(teacher);
        entityManager.flush();

        discipline.setTeacher(teacher);
        discipline.setCourses(Collections.emptyList());

        return discipline;
    }
}
