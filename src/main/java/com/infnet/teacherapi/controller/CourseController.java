package com.infnet.teacherapi.controller;

import com.infnet.teacherapi.dto.AddDisciplineDto;
import com.infnet.teacherapi.dto.CourseDto;
import com.infnet.teacherapi.model.Course;
import com.infnet.teacherapi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CourseDto courseDto) {
        Course course = courseService.createCourse(courseDto);
        return ResponseEntity.ok(course);
    }

    @PostMapping("/addDiscipline")
    public ResponseEntity<Course> addDisciplineToCourse(@Valid @RequestBody AddDisciplineDto addDisciplineDto) {
        Course course = courseService.addDisciplineToCourse(addDisciplineDto);
        return ResponseEntity.ok(course);
    }
}
