package com.infnet.teacherapi.service;

import com.infnet.teacherapi.dto.AddDisciplineDto;
import com.infnet.teacherapi.dto.CourseDto;
import com.infnet.teacherapi.model.Course;
import com.infnet.teacherapi.model.Discipline;
import com.infnet.teacherapi.repository.CourseRepository;
import com.infnet.teacherapi.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    public Course createCourse(CourseDto courseDto) {
        Course course = new Course();
        course.setName(courseDto.getName());

        List<Discipline> disciplines = courseDto.getDisciplinesIds().stream()
                .map(id -> disciplineRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid discipline ID: " + id)))
                .collect(Collectors.toList());

        course.setDisciplines(disciplines);

        return courseRepository.save(course);
    }

    public Course addDisciplineToCourse(AddDisciplineDto addDisciplineDto) {
        Optional<Course> optionalCourse = courseRepository.findById(addDisciplineDto.getCourseId());
        if (optionalCourse.isEmpty()) {
            throw new IllegalArgumentException("Course not found");
        }

        Optional<Discipline> optionalDiscipline = disciplineRepository.findById(addDisciplineDto.getDisciplineId());
        if (optionalDiscipline.isEmpty()) {
            throw new IllegalArgumentException("Discipline not found");
        }

        Course course = optionalCourse.get();
        Discipline discipline = optionalDiscipline.get();

        course.getDisciplines().add(discipline);
        return courseRepository.save(course);
    }
}
