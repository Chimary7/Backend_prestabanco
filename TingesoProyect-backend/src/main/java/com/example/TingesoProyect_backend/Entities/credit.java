package com.example.TingesoProyect_backend.Entities;

import com.example.TingesoProyect_backend.util.CreditStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "credit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private long id;

    @Column(nullable = false)
    private String rutClient;

    @Column(nullable = false)
    private Date createdate = new Date();

    @Column(nullable = false)
    private Integer amountTotal = 0;

    @Column(nullable = false)
    private Boolean processCredit = true;

    @Column(nullable = false)
    private Integer idloanType = 0;

    @Column(nullable = false)
    private Boolean creditEvaluation = false;

    @Column(nullable = false)
    private Boolean savingHistory = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CreditStatus creditStatus = CreditStatus.EN_REVISION_INICIAL;
}
