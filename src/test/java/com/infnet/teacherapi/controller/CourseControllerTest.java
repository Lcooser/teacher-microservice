package com.infnet.teacherapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.teacherapi.dto.AddDisciplineDto;
import com.infnet.teacherapi.dto.CourseDto;
import com.infnet.teacherapi.model.Course;
import com.infnet.teacherapi.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    public void testCreateCourse() throws Exception {
        CourseDto courseDto = new CourseDto();
        courseDto.setName("Test Course");

        Course course = new Course();
        course.setName("Test Course");

        when(courseService.createCourse(any(CourseDto.class))).thenReturn(course);

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Course"));
    }

    @Test
    public void testAddDisciplineToCourse() throws Exception {
        AddDisciplineDto addDisciplineDto = new AddDisciplineDto();
        addDisciplineDto.setCourseId(UUID.randomUUID());
        addDisciplineDto.setDisciplineId(UUID.randomUUID());

        Course course = new Course();
        course.setName("Test Course with Discipline");

        when(courseService.addDisciplineToCourse(any(AddDisciplineDto.class))).thenReturn(course);

        mockMvc.perform(post("/courses/addDiscipline")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addDisciplineDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Course with Discipline"));
    }
}
