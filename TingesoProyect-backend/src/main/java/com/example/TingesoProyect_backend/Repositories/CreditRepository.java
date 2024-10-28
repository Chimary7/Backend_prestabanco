package com.example.TingesoProyect_backend.Repositories;

import com.example.TingesoProyect_backend.Entities.credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<credit, Long> {
    public List<credit> findByRutClient(String rutClient);

    public List<credit> findByProcessCredit(Boolean process);

    public Optional<credit> findByRutClientAndProcessCredit(String rutClient, Boolean process);

    public Optional<credit> findByRutClientAndIdloanType(String rutClient, Integer idLoan);
}
