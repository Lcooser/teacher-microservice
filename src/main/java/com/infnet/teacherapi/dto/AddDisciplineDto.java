package com.infnet.teacherapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddDisciplineDto {
    private UUID courseId;
    private UUID disciplineId;
}
