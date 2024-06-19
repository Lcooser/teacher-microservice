package com.infnet.teacherapi.service;

import com.infnet.teacherapi.model.Discipline;
import com.infnet.teacherapi.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DisciplineServiceImpl {

    @Autowired
    private DisciplineRepository disciplineRepository;

    public Discipline save(Discipline discipline) {
        return disciplineRepository.save(discipline);
    }

    public List<Discipline> findAll() {
        return disciplineRepository.findAll();
    }

    public Optional<Discipline> findById(UUID id) {
        return disciplineRepository.findById(id);
    }
}
