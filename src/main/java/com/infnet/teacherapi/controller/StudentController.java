package com.infnet.teacherapi.controller;

import com.infnet.teacherapi.dto.GradeDto;
import com.infnet.teacherapi.dto.StudentDto;
import com.infnet.teacherapi.model.Grade;
import com.infnet.teacherapi.model.Student;
import com.infnet.teacherapi.service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentDto studentDto) {
        Student student = studentService.createStudent(studentDto);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Student>> getStudentById(@PathVariable UUID id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @PostMapping("/{studentId}/grades/{disciplineId}")
    public ResponseEntity<Grade> assignGradeToStudent(
            @PathVariable UUID studentId,
            @PathVariable UUID disciplineId,
            @Valid @RequestBody GradeDto gradeDto) {

        Grade grade = studentService.assignGrade(studentId, disciplineId, gradeDto);
        return ResponseEntity.ok(grade);
    }

    @GetMapping("/disciplines/{disciplineId}/approved")
    public ResponseEntity<List<Student>> getApprovedStudentsByDiscipline(@PathVariable UUID disciplineId) {
        List<Student> approvedStudents = studentService.findApprovedStudentsByDiscipline(disciplineId);
        return ResponseEntity.ok(approvedStudents);
    }

    @GetMapping("/disciplines/{disciplineId}/failed")
    public ResponseEntity<List<Student>> getFailedStudentsByDiscipline(@PathVariable UUID disciplineId) {
        List<Student> failedStudents = studentService.findFailedStudentsByDiscipline(disciplineId);
        return ResponseEntity.ok(failedStudents);
    }
}
