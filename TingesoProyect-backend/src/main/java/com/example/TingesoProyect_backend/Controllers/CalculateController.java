package com.example.TingesoProyect_backend.Controllers;

import com.example.TingesoProyect_backend.Services.CalculateService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Calculate")
@CrossOrigin("*")
public class CalculateController {
    @Autowired
    CalculateService calculateService;

    @GetMapping("/")
    public ResponseEntity<Integer> CalculateSimulator(@RequestParam Double CapitalMonth,
                                                     @RequestParam Double annualInterest,
                                                     @RequestParam Integer years){
        Integer monthlyPayment = calculateService.SimulatorCalculate(CapitalMonth,annualInterest,years);
        return ResponseEntity.ok(monthlyPayment);
    }
}
