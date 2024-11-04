package com.example.TingesoProyect_backend.Controllers;

import com.example.TingesoProyect_backend.Entities.LoanType;
import com.example.TingesoProyect_backend.Services.LoanTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestabanco/loantype")
@CrossOrigin("*")
public class LoanTypeController {
    @Autowired
    LoanTypeService loanTypeService;

    @GetMapping("/")
    public ResponseEntity<List<LoanType>> ListLoanType(){
        List<LoanType> loanTypes = loanTypeService.getAllLoanType();
        return ResponseEntity.ok(loanTypes);
    }

    @GetMapping("/loan")
    public ResponseEntity<LoanType> GetLoanTypeById(@RequestParam long id){
        LoanType loanType = loanTypeService.getLoanTypeById(id);
        return ResponseEntity.ok(loanType);
    }

    @GetMapping("/{name}")
    public ResponseEntity<LoanType> GetLoanTypeByName(@PathVariable String name){
        LoanType loanType = loanTypeService.getLoanTypebyName(name);
        return ResponseEntity.ok(loanType);
    }

    @PostMapping("/")
    public ResponseEntity<LoanType> SaveLoanType(@RequestBody LoanType loanType){
        LoanType loanTypeNew = loanTypeService.saveLoanType(loanType);
        return ResponseEntity.ok(loanTypeNew);
    }

    @PutMapping("/")
    public ResponseEntity<LoanType> UpdateLoanType (@RequestBody LoanType loanType){
        LoanType newLoanType = loanTypeService.updateLoanType(loanType);
        return ResponseEntity.ok(newLoanType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> DeleteLoanTypeId(@PathVariable Long id) throws Exception{
        var isdeleted = loanTypeService.deleteLoanType(id);
        return ResponseEntity.noContent().build();
    }
}
