package com.infnet.teacherapi.service;

import com.infnet.teacherapi.dto.GradeDto;
import com.infnet.teacherapi.dto.StudentDto;
import com.infnet.teacherapi.model.*;
import com.infnet.teacherapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private GradeRepository gradeRepository;

    public Student createStudent(StudentDto studentDto) {
        Student student = new Student();
        student.setName(studentDto.getName());
        student.setEmail(studentDto.getEmail());
        student.setCellphone(studentDto.getCellphone());
        student.setAddress(studentDto.getAddress());
        student.setRole(studentDto.getRole());

        Course course = courseRepository.findById(studentDto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
        student.setCourse(course);


        List<Discipline> disciplines = studentDto.getDisciplineIds().stream()
                .map(id -> disciplineRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid discipline ID: " + id)))
                .collect(Collectors.toList());
        student.setDisciplines(disciplines);

        return studentRepository.save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(UUID id) {
        return studentRepository.findById(id);
    }

    @Transactional
    public Grade assignGrade(UUID studentId, UUID disciplineId, GradeDto gradeDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        Discipline discipline = disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new IllegalArgumentException("Discipline not found with ID: " + disciplineId));

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setDiscipline(discipline);
        grade.setGrade(gradeDto.getGrade());

        return gradeRepository.save(grade);
    }

    public List<Student> findApprovedStudentsByDiscipline(UUID disciplineId) {
        List<Student> approvedStudents = new ArrayList<>();


        List<Grade> grades = gradeRepository.findByDisciplineId(disciplineId);


        for (Grade grade : grades) {
            if (grade.getGrade() >= 7.0) {
                approvedStudents.add(grade.getStudent());
            }
        }

        return approvedStudents;
    }

    public List<Student> findFailedStudentsByDiscipline(UUID disciplineId) {
        List<Student> failedStudents = new ArrayList<>();


        List<Grade> grades = gradeRepository.findByDisciplineId(disciplineId);


        for (Grade grade : grades) {
            if (grade.getGrade() < 7.0) {
                failedStudents.add(grade.getStudent());
            }
        }

        return failedStudents;
    }
}
