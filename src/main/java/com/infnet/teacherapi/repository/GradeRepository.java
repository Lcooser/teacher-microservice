package com.infnet.teacherapi.repository;

import com.infnet.teacherapi.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GradeRepository extends JpaRepository<Grade, UUID> {

    List<Grade> findByDisciplineId(UUID disciplineId);

}
