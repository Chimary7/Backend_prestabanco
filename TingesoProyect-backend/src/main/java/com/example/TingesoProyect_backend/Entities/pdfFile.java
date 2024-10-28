package com.example.TingesoProyect_backend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pdffile")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class pdfFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

    private String name;

    @Lob
    private byte[] data;

    private Long creditid;
}
