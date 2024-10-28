package com.example.TingesoProyect_backend.Services;

import com.example.TingesoProyect_backend.Repositories.LoanTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculateService {
    @Autowired
    private LoanTypeRepository loanTypeRepository;

    public Integer SimulatorCalculate(Double CapitalMont, Double AnnualInterest, Integer years){
        if (CapitalMont <= 0.0 || AnnualInterest <= 0.0 || years <= 0){
            throw new IllegalArgumentException("por favor ingrese valores en los parametros que sea distinto y mayor que 0");
        }
        double MonthlyInterest = (AnnualInterest / 12) / 100;
        int totalPayments = years * 12;
        double MonthlyPayments = (CapitalMont * MonthlyInterest * Math.pow(1+MonthlyInterest, totalPayments))
                /
                (Math.pow(1+MonthlyInterest, totalPayments)-1);
        return (int) Math.round(MonthlyPayments);
    }
}
