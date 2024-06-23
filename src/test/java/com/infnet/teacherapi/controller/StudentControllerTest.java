package com.infnet.teacherapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.teacherapi.dto.GradeDto;
import com.infnet.teacherapi.dto.StudentDto;
import com.infnet.teacherapi.model.Discipline;
import com.infnet.teacherapi.model.Grade;
import com.infnet.teacherapi.model.Student;
import com.infnet.teacherapi.service.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StudentControllerTest {

    @Mock
    private StudentServiceImpl studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void testCreateStudent() throws Exception {

        StudentDto studentDto = new StudentDto();
        studentDto.setName("Luiz Coser");
        studentDto.setEmail("luiz.coser@infnet.com");
        studentDto.setCellphone("1234567890");
        studentDto.setAddress("Rio de Janeiro");
        studentDto.setRole("Student");
        studentDto.setCourseId(UUID.randomUUID());
        studentDto.setDisciplineIds(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));


        Student expectedStudent = new Student();
        expectedStudent.setName("Luiz Coser");
        expectedStudent.setEmail("luiz.coser@infnet.com");
        expectedStudent.setCellphone("1234567890");
        expectedStudent.setAddress("Rio de Janeiro");
        expectedStudent.setRole("Student");


        when(studentService.createStudent(any(StudentDto.class))).thenReturn(expectedStudent);


        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Luiz Coser"))
                .andExpect(jsonPath("$.email").value("luiz.coser@infnet.com"))
                .andExpect(jsonPath("$.cellphone").value("1234567890"))
                .andExpect(jsonPath("$.address").value("Rio de Janeiro"))
                .andExpect(jsonPath("$.role").value("Student"));
    }


    @Test
    public void testGetAllStudents() throws Exception {
        Student student1 = new Student();
        student1.setName("Student 1");

        Student student2 = new Student();
        student2.setName("Student 2");

        when(studentService.findAll()).thenReturn(Arrays.asList(student1, student2));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Student 1"))
                .andExpect(jsonPath("$[1].name").value("Student 2"));
    }

    @Test
    public void testGetStudentById() throws Exception {
        UUID id = UUID.randomUUID();
        Student student = new Student();
        student.setId(id);
        student.setName("Test Student");

        when(studentService.findById(id)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Test Student"));
    }


    @Test
    public void testGetApprovedStudentsByDiscipline() throws Exception {
        UUID disciplineId = UUID.randomUUID();
        Student student1 = new Student();
        student1.setName("Approved Student 1");

        Student student2 = new Student();
        student2.setName("Approved Student 2");

        when(studentService.findApprovedStudentsByDiscipline(disciplineId)).thenReturn(Arrays.asList(student1, student2));

        mockMvc.perform(get("/students/disciplines/{disciplineId}/approved", disciplineId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Approved Student 1"))
                .andExpect(jsonPath("$[1].name").value("Approved Student 2"));
    }

    @Test
    public void testGetFailedStudentsByDiscipline() throws Exception {
        UUID disciplineId = UUID.randomUUID();
        Student student1 = new Student();
        student1.setName("Failed Student 1");

        Student student2 = new Student();
        student2.setName("Failed Student 2");

        when(studentService.findFailedStudentsByDiscipline(disciplineId)).thenReturn(Arrays.asList(student1, student2));

        mockMvc.perform(get("/students/disciplines/{disciplineId}/failed", disciplineId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Failed Student 1"))
                .andExpect(jsonPath("$[1].name").value("Failed Student 2"));
    }
}
