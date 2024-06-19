package com.infnet.teacherapi.repository;

import com.infnet.teacherapi.model.Teacher;
import com.infnet.teacherapi.security.UserDetailsServicecImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    Teacher findByLogin(String login);

}
