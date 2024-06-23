package com.infnet.teacherapi.controller;

import com.infnet.teacherapi.model.Discipline;
import com.infnet.teacherapi.service.DisciplineServiceImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DisciplineControllerTest {

    @Mock
    private DisciplineServiceImpl disciplineService;

    @InjectMocks
    private DisciplineController disciplineController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(disciplineController).build();
    }

    @Test
    public void testCreateDiscipline() throws Exception {
        Discipline discipline = new Discipline();
        discipline.setName("Test Discipline");

        when(disciplineService.save(any(Discipline.class))).thenReturn(discipline);

        mockMvc.perform(post("/disciplines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(discipline)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Discipline"));
    }

    @Test
    public void testGetAllDisciplines() throws Exception {
        Discipline discipline1 = new Discipline();
        discipline1.setName("Discipline 1");

        Discipline discipline2 = new Discipline();
        discipline2.setName("Discipline 2");

        when(disciplineService.findAll()).thenReturn(Arrays.asList(discipline1, discipline2));

        mockMvc.perform(get("/disciplines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Discipline 1"))
                .andExpect(jsonPath("$[1].name").value("Discipline 2"));
    }

    @Test
    public void testGetDisciplineById() throws Exception {
        UUID id = UUID.randomUUID();
        Discipline discipline = new Discipline();
        discipline.setName("Test Discipline");

        when(disciplineService.findById(id)).thenReturn(Optional.of(discipline));

        mockMvc.perform(get("/disciplines/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Discipline"));
    }

}
