package com.example.TingesoProyect_backend.Controllers;

import com.example.TingesoProyect_backend.Services.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/Calculate")
@CrossOrigin("*")
public class CalculateController {
    @Autowired
    CalculateService calculateService;

    @GetMapping("/")
    public ResponseEntity<Integer> CalculateSimulator(@RequestParam Double CapitalMonth,
                                                      @RequestParam Double annualInterest,
                                                      @RequestParam Integer years) {
        Integer monthlyPayment = calculateService.SimulatorCalculate(CapitalMonth, annualInterest, years);
        return ResponseEntity.ok(monthlyPayment);
    }

    @GetMapping("/costtotal")
    public ResponseEntity<Integer> CalculateCostTotal(@RequestParam Double Month,
                                                      @RequestParam Double annualInterest,
                                                      @RequestParam Integer years) {
        Integer MonthCostTotal = calculateService.CostTotal(Month, annualInterest, years);
        return ResponseEntity.ok(MonthCostTotal);
    }

    @GetMapping("/cuotaingreso")
    public ResponseEntity<Double> CalculateCuotaIngreso(@RequestParam Double cuotaMensual,
                                                        @RequestParam Double ingreso) {
        Double Relation = calculateService.RelacionCuotaIngreso(cuotaMensual, ingreso);
        return ResponseEntity.ok(Relation);
    }

    @GetMapping("/deudaingreso")
    public ResponseEntity<Double> CalculateDeudaIngreso(@RequestParam Double cuotaMensual,
                                                        @RequestParam Integer MontoDeudas,
                                                        @RequestParam Integer ingreso) {
        Double Relation = calculateService.RelacionDeudaIngreso(cuotaMensual, MontoDeudas, ingreso);
        return ResponseEntity.ok(Relation);
    }

    @GetMapping("/edadsolicitante")
    public ResponseEntity<Boolean> edadsolicitante(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate, @RequestParam Integer TimeLoan){
        Boolean decisive = calculateService.yearSolicited(birthdate, TimeLoan);
        return ResponseEntity.ok(decisive);
    }

    @GetMapping("/minsaving")
    public ResponseEntity<Boolean> minsaving(@RequestParam Integer MonthPay, @RequestParam Integer MonthSaving){
        Boolean decisive = calculateService.minPaySaving(MonthPay,MonthSaving);
        return ResponseEntity.ok(decisive);
    }

    @GetMapping("/relationmonthsavingyears")
    public ResponseEntity<Boolean> relationMonthSavingYears(@RequestParam Integer MonthPay,
                                                            @RequestParam Integer MonthSaving,
                                                            @RequestParam Integer Years){
        Boolean decisive = calculateService.RelationMonthSavingYears(MonthPay,MonthSaving,Years);
        return ResponseEntity.ok(decisive);
    }
}
