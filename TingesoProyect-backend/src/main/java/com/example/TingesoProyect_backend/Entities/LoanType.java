package com.example.TingesoProyect_backend.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    @Column(unique = true)
    private String nameLoan;

    @Column(nullable = false)
    private Integer maxTime = 0;

    @Column(nullable = false)
    private Double minInterest = 0.0;

    @Column(nullable = false)
    private Double maxInterest = 0.0;

    @Column(nullable = false)
    private Double maxFinanPorcent = 0.0;

    @ElementCollection
    private List<String> requirements;
}
