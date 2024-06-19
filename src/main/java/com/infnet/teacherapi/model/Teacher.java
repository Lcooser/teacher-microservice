package com.infnet.teacherapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

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

    @NotEmpty(message = "The field password cannot be null or empty")
    private String password;

    @NotEmpty(message = "The field login cannot be null or empty")
    private String login;

    @ManyToMany(mappedBy = "teachers")
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    private List<Discipline> disciplines = new ArrayList<>();

}
