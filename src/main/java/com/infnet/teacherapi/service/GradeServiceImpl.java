package com.infnet.teacherapi.service;

import com.infnet.teacherapi.model.Discipline;
import com.infnet.teacherapi.model.Grade;
import com.infnet.teacherapi.model.Student;
import com.infnet.teacherapi.repository.DisciplineRepository;
import com.infnet.teacherapi.repository.GradeRepository;
import com.infnet.teacherapi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GradeServiceImpl {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    public Grade assignGrade(UUID studentId, UUID disciplineId, double gradeValue) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID"));

        Discipline discipline = disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid discipline ID"));

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setDiscipline(discipline);
        grade.setGrade(gradeValue);

        return gradeRepository.save(grade);
    }
}
