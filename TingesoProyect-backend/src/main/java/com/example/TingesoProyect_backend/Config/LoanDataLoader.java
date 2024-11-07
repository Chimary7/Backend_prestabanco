package com.example.TingesoProyect_backend.Config;

import com.example.TingesoProyect_backend.Entities.LoanType;
import com.example.TingesoProyect_backend.Repositories.LoanTypeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class LoanDataLoader implements CommandLineRunner {
    @Autowired
    private LoanTypeRepository loanTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (loanTypeRepository.count() == 0) { // Load data only if the database is empty
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<LoanType>> typeReference = new TypeReference<List<LoanType>>() {};
            InputStream inputStream = new ClassPathResource("loans.json").getInputStream();
            try {
                List<LoanType> loanTypes = objectMapper.readValue(inputStream, typeReference);
                loanTypeRepository.saveAll(loanTypes);
                System.out.println("Loan data loaded successfully.");
            } catch (IOException e) {
                System.out.println("Unable to load loan data: " + e.getMessage());
            }
        }
    }
}
