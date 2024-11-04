package com.example.TingesoProyect_backend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private long id;

    @Column(unique = true)
    private String rut;

    private String name;

    private String lastname;

    private Date birthdate;

    @Column(nullable = false)
    private Integer Ingreso = 0;

    @Column(nullable = false)
    private Boolean register = false;

}
