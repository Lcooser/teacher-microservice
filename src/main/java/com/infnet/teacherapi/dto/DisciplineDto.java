package com.infnet.teacherapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class DisciplineDto {

    private UUID id;

    @NotEmpty(message = "The field name cannot be null or empty")
    @Size(min = 2, max = 60, message = "The field name must be between 2 and 60 characters")
    private String name;

    @NotEmpty(message = "The field code cannot be null or empty")
    private String code;

    private UUID teacherId;

    @NotNull(message = "The field courses cannot be null or empty")
    private List<UUID> courseIds = new ArrayList<>();
}
