package com.infnet.teacherapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class StudentDto  {

    private UUID id;
    private String name;
    private String email;
    private String cellphone;
    private String address;
    private String role;

    private List<GradeDto> disciplinesWithGrades = new ArrayList<>();

    @NotNull(message = "The field course cannot be null")
    private UUID courseId;

    @NotNull(message = "The field disciplines cannot be null")
    private List<UUID> disciplineIds = new ArrayList<>();

    private List<UUID> teacherIds = new ArrayList<>();
}

