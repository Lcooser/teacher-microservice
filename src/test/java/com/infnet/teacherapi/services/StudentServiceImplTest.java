package com.infnet.teacherapi.services;

import com.infnet.teacherapi.dto.GradeDto;
import com.infnet.teacherapi.dto.StudentDto;
import com.infnet.teacherapi.model.*;
import com.infnet.teacherapi.repository.*;
import com.infnet.teacherapi.service.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private DisciplineRepository disciplineRepository;

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    public StudentServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateStudent() {
        StudentDto studentDto = new StudentDto();
        studentDto.setName("Luiz Coser");
        studentDto.setEmail("luizcoser@infnet.com");
        studentDto.setCellphone("123456789");
        studentDto.setAddress("São Paulo");
        studentDto.setRole("Teste");
        studentDto.setCourseId(UUID.randomUUID());
        studentDto.setDisciplineIds(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));

        Course course = new Course();
        Discipline discipline1 = new Discipline();
        Discipline discipline2 = new Discipline();

        when(courseRepository.findById(studentDto.getCourseId())).thenReturn(Optional.of(course));
        when(disciplineRepository.findById(any(UUID.class))).thenReturn(Optional.of(discipline1), Optional.of(discipline2));
        when(studentRepository.save(any(Student.class))).thenAnswer(i -> i.getArguments()[0]);

        Student student = studentService.createStudent(studentDto);

        assertEquals("Luiz Coser", student.getName());
        assertEquals("luizcoser@infnet.com", student.getEmail());
        assertEquals("123456789", student.getCellphone());
        assertEquals("São Paulo", student.getAddress());
        assertEquals("Teste", student.getRole());
        assertEquals(course, student.getCourse());
        assertEquals(2, student.getDisciplines().size());
    }

    @Test
    public void testFindAll() {
        Student student1 = new Student();
        Student student2 = new Student();

        when(studentRepository.findAll()).thenReturn(List.of(student1, student2));

        List<Student> students = studentService.findAll();

        assertEquals(2, students.size());
    }

    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        Student student = new Student();

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        Optional<Student> foundStudent = studentService.findById(id);

        assertEquals(student, foundStudent.get());
    }

    @Test
    @Transactional
    public void testAssignGrade() {
        UUID studentId = UUID.randomUUID();
        UUID disciplineId = UUID.randomUUID();
        GradeDto gradeDto = new GradeDto();
        gradeDto.setGrade(8.5);

        Student student = new Student();
        Discipline discipline = new Discipline();
        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setDiscipline(discipline);
        grade.setGrade(gradeDto.getGrade());

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(disciplineRepository.findById(disciplineId)).thenReturn(Optional.of(discipline));
        when(gradeRepository.save(any(Grade.class))).thenAnswer(i -> i.getArguments()[0]);

        Grade assignedGrade = studentService.assignGrade(studentId, disciplineId, gradeDto);

        assertEquals(8.5, assignedGrade.getGrade());
        assertEquals(student, assignedGrade.getStudent());
        assertEquals(discipline, assignedGrade.getDiscipline());
    }

    @Test
    public void testAssignGrade_StudentNotFound() {
        UUID studentId = UUID.randomUUID();
        UUID disciplineId = UUID.randomUUID();
        GradeDto gradeDto = new GradeDto();
        gradeDto.setGrade(8.5);

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> studentService.assignGrade(studentId, disciplineId, gradeDto));
    }

    @Test
    public void testAssignGrade_DisciplineNotFound() {
        UUID studentId = UUID.randomUUID();
        UUID disciplineId = UUID.randomUUID();
        GradeDto gradeDto = new GradeDto();
        gradeDto.setGrade(8.5);

        Student student = new Student();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(disciplineRepository.findById(disciplineId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> studentService.assignGrade(studentId, disciplineId, gradeDto));
    }

    @Test
    public void testFindApprovedStudentsByDiscipline() {
        UUID disciplineId = UUID.randomUUID();
        Grade grade1 = new Grade();
        Grade grade2 = new Grade();
        Grade grade3 = new Grade();

        Student student1 = new Student();
        Student student2 = new Student();

        grade1.setGrade(8.0);
        grade1.setStudent(student1);
        grade2.setGrade(9.0);
        grade2.setStudent(student2);
        grade3.setGrade(5.0);

        when(gradeRepository.findByDisciplineId(disciplineId)).thenReturn(List.of(grade1, grade2, grade3));

        List<Student> approvedStudents = studentService.findApprovedStudentsByDiscipline(disciplineId);

        assertEquals(2, approvedStudents.size());
        assertEquals(student1, approvedStudents.get(0));
        assertEquals(student2, approvedStudents.get(1));
    }

    @Test
    public void testFindFailedStudentsByDiscipline() {
        UUID disciplineId = UUID.randomUUID();
        Grade grade1 = new Grade();
        Grade grade2 = new Grade();
        Grade grade3 = new Grade();

        Student student3 = new Student();

        grade1.setGrade(8.0);
        grade2.setGrade(9.0);
        grade3.setGrade(5.0);
        grade3.setStudent(student3);

        when(gradeRepository.findByDisciplineId(disciplineId)).thenReturn(List.of(grade1, grade2, grade3));

        List<Student> failedStudents = studentService.findFailedStudentsByDiscipline(disciplineId);

        assertEquals(1, failedStudents.size());
        assertEquals(student3, failedStudents.get(0));
    }
}
