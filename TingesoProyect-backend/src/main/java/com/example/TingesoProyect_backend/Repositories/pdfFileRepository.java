package com.example.TingesoProyect_backend.Repositories;

import com.example.TingesoProyect_backend.Entities.pdfFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface pdfFileRepository extends JpaRepository<pdfFile,Long> {

    List<pdfFile> findByCreditid(Long creditId);
    pdfFile findByCategoryAndCreditid(String category, Long creditid);
}
