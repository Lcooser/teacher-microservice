package com.infnet.teacherapi.controller;

import com.infnet.teacherapi.model.Grade;
import com.infnet.teacherapi.service.GradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeServiceImpl gradeService;

    @PostMapping("/assign-grade/{studentId}/{disciplineId}/{gradeValue}")
    public ResponseEntity<Grade> assignGrade(
            @PathVariable UUID studentId,
            @PathVariable UUID disciplineId,
            @PathVariable double gradeValue) {
        Grade grade = gradeService.assignGrade(studentId, disciplineId, gradeValue);
        return ResponseEntity.ok(grade);
    }
}
