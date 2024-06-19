package com.infnet.teacherapi.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TeacherDto  {

    private UUID id;
    private String name;
    private String email;
    private String cellphone;
    private String address;
    private String role;

    private List<UUID> disciplineIds = new ArrayList<>();
    private List<UUID> studentIds = new ArrayList<>();
}

