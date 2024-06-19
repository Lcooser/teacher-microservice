package com.infnet.teacherapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GradeDto {

    private UUID id;
    private UUID studentId;
    private UUID disciplineId;
    private double grade;
}
