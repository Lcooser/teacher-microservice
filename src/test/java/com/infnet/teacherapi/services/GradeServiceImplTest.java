package com.infnet.teacherapi.services;

import com.infnet.teacherapi.model.Discipline;
import com.infnet.teacherapi.model.Grade;
import com.infnet.teacherapi.model.Student;
import com.infnet.teacherapi.repository.DisciplineRepository;
import com.infnet.teacherapi.repository.GradeRepository;
import com.infnet.teacherapi.repository.StudentRepository;
import com.infnet.teacherapi.service.GradeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GradeServiceImplTest {

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private DisciplineRepository disciplineRepository;

    @InjectMocks
    private GradeServiceImpl gradeService;

    public GradeServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignGrade() {
        UUID studentId = UUID.randomUUID();
        UUID disciplineId = UUID.randomUUID();
        double gradeValue = 8.5;

        Student student = new Student();
        Discipline discipline = new Discipline();
        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setDiscipline(discipline);
        grade.setGrade(gradeValue);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(disciplineRepository.findById(disciplineId)).thenReturn(Optional.of(discipline));
        when(gradeRepository.save(any(Grade.class))).thenAnswer(i -> i.getArguments()[0]);

        Grade assignedGrade = gradeService.assignGrade(studentId, disciplineId, gradeValue);

        assertEquals(8.5, assignedGrade.getGrade());
        assertEquals(student, assignedGrade.getStudent());
        assertEquals(discipline, assignedGrade.getDiscipline());
    }

    @Test
    public void testAssignGrade_StudentNotFound() {
        UUID studentId = UUID.randomUUID();
        UUID disciplineId = UUID.randomUUID();
        double gradeValue = 8.5;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> gradeService.assignGrade(studentId, disciplineId, gradeValue));
    }

    @Test
    public void testAssignGrade_DisciplineNotFound() {
        UUID studentId = UUID.randomUUID();
        UUID disciplineId = UUID.randomUUID();
        double gradeValue = 8.5;

        Student student = new Student();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(disciplineRepository.findById(disciplineId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> gradeService.assignGrade(studentId, disciplineId, gradeValue));
    }
}
