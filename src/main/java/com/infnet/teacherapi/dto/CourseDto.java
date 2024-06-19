package com.infnet.teacherapi.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CourseDto {

    private UUID id;

    @NotEmpty(message = "The field name cannot be null or empty")
    @Size(min = 2, max = 60)
    private String name;

    private List<UUID> studentsIds = new ArrayList<>();

    private List<UUID> disciplinesIds = new ArrayList<>();
}

