package com.example.TingesoProyect_backend.Services;

import com.example.TingesoProyect_backend.Entities.pdfFile;
import com.example.TingesoProyect_backend.Repositories.pdfFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class pdfFileService {
    @Autowired
    private pdfFileRepository pdfFileRepository;

    public ArrayList<pdfFile> getAllFiles(){
        return (ArrayList<pdfFile>) pdfFileRepository.findAll();
    }

    public List<pdfFile> getPdfFilesByCredit(Long creditId){
        return pdfFileRepository.findByCreditid(creditId);
    }

    public pdfFile getPdfFileByCategoryAndCredit(String category, Long creditId){
        return pdfFileRepository.findByCategoryAndCreditid(category, creditId);
    };

    public pdfFile StoreFile(MultipartFile File, String category, Long creditId) throws IOException {
        pdfFile fileEntity = new pdfFile();
        fileEntity.setName(File.getOriginalFilename());
        fileEntity.setCategory(category);
        fileEntity.setData(File.getBytes());
        fileEntity.setCreditid(creditId);
        return pdfFileRepository.save(fileEntity);
    }

    public pdfFile updateFile(Long id, MultipartFile file, String category, Long creditId) throws Exception{
        Optional<pdfFile> existingFile = pdfFileRepository.findById(id);
        if (existingFile.isPresent()) {
            pdfFile fileEntity = existingFile.get();
            fileEntity.setName(file.getOriginalFilename());
            fileEntity.setCategory(category);
            fileEntity.setData(file.getBytes());
            fileEntity.setCreditid(creditId);
            return pdfFileRepository.save(fileEntity);
        } else {
            throw new Exception("Archivo no encontrado");
        }
    }

    public boolean deletePdfFile(Long id) throws Exception{
        try {
            pdfFileRepository.deleteById(id);
            return true;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
