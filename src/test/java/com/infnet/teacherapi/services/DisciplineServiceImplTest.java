package com.infnet.teacherapi.services;

import com.infnet.teacherapi.model.Discipline;
import com.infnet.teacherapi.repository.DisciplineRepository;
import com.infnet.teacherapi.service.DisciplineServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DisciplineServiceImplTest {

    @Mock
    private DisciplineRepository disciplineRepository;

    @InjectMocks
    private DisciplineServiceImpl disciplineService;

    public DisciplineServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        Discipline discipline = new Discipline();
        discipline.setName("Test Discipline");

        when(disciplineRepository.save(any(Discipline.class))).thenAnswer(i -> i.getArguments()[0]);

        Discipline savedDiscipline = disciplineService.save(discipline);

        assertEquals("Test Discipline", savedDiscipline.getName());
    }

    @Test
    public void testFindAll() {
        Discipline discipline1 = new Discipline();
        Discipline discipline2 = new Discipline();

        when(disciplineRepository.findAll()).thenReturn(List.of(discipline1, discipline2));

        List<Discipline> disciplines = disciplineService.findAll();

        assertEquals(2, disciplines.size());
    }

    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        Discipline discipline = new Discipline();

        when(disciplineRepository.findById(id)).thenReturn(Optional.of(discipline));

        Optional<Discipline> foundDiscipline = disciplineService.findById(id);

        assertEquals(discipline, foundDiscipline.get());
    }
}
