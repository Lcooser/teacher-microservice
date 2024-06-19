package com.infnet.teacherapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotEmpty(message = "The field name cannot be null or empty")
    private String name;

    @NotEmpty(message = "The field email cannot be null or empty")
    private String email;

    @NotEmpty(message = "The field cellphone cannot be null or empty")
    private String cellphone;

    @NotEmpty(message = "The field address cannot be null or empty")
    private String address;

    @NotEmpty(message = "The field role cannot be null or empty")
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_disciplines",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "discipline_id")
    )
    private List<Discipline> disciplines = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_teachers",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<Teacher> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grade> grades = new ArrayList<>();

    public void create(String name, String cpf, String email, String cellphone, String address, String role, Course course, List<Discipline> disciplines, List<Teacher> teachers) {
        if (!isValidCpf(cpf)) {
            throw new IllegalArgumentException("Invalid CPF");
        }
        if (!"Student".equals(role)) {
            throw new IllegalArgumentException("Invalid role");
        }

        this.name = name;
        this.email = email;
        this.cellphone = cellphone;
        this.address = address;
        this.role = role;
        this.course = course;
        this.disciplines = disciplines;
        this.teachers = teachers;
    }

    public boolean isValidCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }
        for (int i = 0; i < 11; i++) {
            if (!Character.isDigit(cpf.charAt(i))) {
                return false;
            }
        }
        if (isAllDigitsTheSame(cpf)) {
            return false;
        }
        int digitOne = calculateCheckDigit(cpf.substring(0, 9));
        int digitTwo = calculateCheckDigit(cpf.substring(0, 10));
        return cpf.equals(cpf.substring(0, 9) + digitOne + digitTwo);
    }

    private boolean isAllDigitsTheSame(String cpf) {
        char firstDigit = cpf.charAt(0);
        for (int i = 1; i < 11; i++) {
            if (cpf.charAt(i) != firstDigit) {
                return false;
            }
        }
        return true;
    }

    private int calculateCheckDigit(String cpfPartial) {
        int weight = cpfPartial.length() + 1;
        int total = 0;
        for (int i = 0; i < cpfPartial.length(); i++) {
            total += Character.getNumericValue(cpfPartial.charAt(i)) * weight--;
        }
        int remainder = total % 11;
        int digit = 11 - remainder;
        return digit > 9 ? 0 : digit;
    }
}
