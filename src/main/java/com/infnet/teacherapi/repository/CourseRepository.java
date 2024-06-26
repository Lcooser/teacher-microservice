package com.infnet.teacherapi.repository;

import com.infnet.teacherapi.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
}
