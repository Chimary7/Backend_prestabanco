package com.example.TingesoProyect_backend.Repositories;

import com.example.TingesoProyect_backend.Entities.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanTypeRepository extends JpaRepository<LoanType,Long> {
    public LoanType findByNameLoan(String nameLoan);

    public LoanType findById(long id);
}
