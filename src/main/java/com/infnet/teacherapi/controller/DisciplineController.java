package com.infnet.teacherapi.controller;

import com.infnet.teacherapi.model.Discipline;
import com.infnet.teacherapi.service.DisciplineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/disciplines")
public class DisciplineController {

    @Autowired
    private DisciplineServiceImpl disciplineService;

    @PostMapping
    public Discipline createDiscipline(@RequestBody Discipline discipline) {
        return disciplineService.save(discipline);
    }

    @GetMapping
    public List<Discipline> getAllDisciplines() {
        return disciplineService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Discipline> getDisciplineById(@PathVariable UUID id) {
        return disciplineService.findById(id);
    }
}
