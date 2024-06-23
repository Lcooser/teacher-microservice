package com.infnet.teacherapi.services;

import com.infnet.teacherapi.dto.AddDisciplineDto;
import com.infnet.teacherapi.dto.CourseDto;
import com.infnet.teacherapi.model.Course;
import com.infnet.teacherapi.model.Discipline;
import com.infnet.teacherapi.repository.CourseRepository;
import com.infnet.teacherapi.repository.DisciplineRepository;
import com.infnet.teacherapi.service.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private DisciplineRepository disciplineRepository;

    @InjectMocks
    private CourseService courseService;

    public CourseServiceTest() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testAddDisciplineToCourse_CourseNotFound() {
        UUID courseId = UUID.randomUUID();
        UUID disciplineId = UUID.randomUUID();

        AddDisciplineDto addDisciplineDto = new AddDisciplineDto();
        addDisciplineDto.setCourseId(courseId);
        addDisciplineDto.setDisciplineId(disciplineId);

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> courseService.addDisciplineToCourse(addDisciplineDto));
    }

    @Test
    public void testAddDisciplineToCourse_DisciplineNotFound() {
        UUID courseId = UUID.randomUUID();
        UUID disciplineId = UUID.randomUUID();

        AddDisciplineDto addDisciplineDto = new AddDisciplineDto();
        addDisciplineDto.setCourseId(courseId);
        addDisciplineDto.setDisciplineId(disciplineId);

        Course course = new Course();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(disciplineRepository.findById(disciplineId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> courseService.addDisciplineToCourse(addDisciplineDto));
    }
}
