package com.example.TingesoProyect_backend.Entities;

import com.example.TingesoProyect_backend.util.CreditStatus;
import com.example.TingesoProyect_backend.util.SavingStatus;
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

    private double maxPorcentFinancy = 0.0;

    private double porcentInterest;

    private Integer timePay;

    private String coment;

    @Column(nullable = false)
    private Boolean processCredit = true;

    @Column(nullable = false)
    private Integer idloanType = 0;

    @Column(nullable = false)
    private Boolean creditEvaluation = false;

    @Enumerated(EnumType.STRING)
    private SavingStatus savingStatus;

    @Column(nullable = false)
    private Boolean savingHistory = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CreditStatus creditStatus = CreditStatus.EN_REVISION_INICIAL;
}
