package com.example.TingesoProyect_backend.Controllers;

import com.example.TingesoProyect_backend.Entities.pdfFile;
import com.example.TingesoProyect_backend.Services.pdfFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/prestabanco/files")
@CrossOrigin("*")
public class pdfFileController {
    @Autowired
    private pdfFileService pdfFileservice;

    @GetMapping("/")
    public ResponseEntity<List<pdfFile>> getAllFiles(){
        List<pdfFile> pdfFiles = pdfFileservice.getAllFiles();
        return ResponseEntity.ok(pdfFiles);
    }

    @GetMapping("/creditid")
    public ResponseEntity<List<pdfFile>> getAllCreditFile(@RequestParam Long idCredit){
        List<pdfFile> pdfFiles = pdfFileservice.getPdfFilesByCredit(idCredit);
        return ResponseEntity.ok(pdfFiles);
    }

    @GetMapping("/pdfcategorycredit")
    public ResponseEntity<byte[]> getPdfFileCreditCategory(@RequestParam String Category, @RequestParam Long idCredit){
        pdfFile pdffile = pdfFileservice.getPdfFileByCategoryAndCredit(Category,idCredit);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.add("Content-Disposition","attachment; filename=" + pdffile.getName());

        return new ResponseEntity<>(pdffile.getData(),headers, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<String> savePdfFile(@RequestParam("category") String category,
                                               @RequestParam("idCredit") Long idCredit,
                                               @RequestParam("data") MultipartFile file){

        try{
            pdfFileservice.StoreFile(file,category,idCredit);
            return ResponseEntity.ok("archivo subido exitosamente");
        } catch (Exception e){
            return ResponseEntity.status(500).body("error al subir el archivo");
        }
    }

    @PutMapping("/")
    public ResponseEntity<String> updatePdfFile(@RequestParam("id") Long id,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam("category") String category,
                                                 @RequestParam("creditid") Long creditId){
        try {
            pdfFileservice.updateFile(id, file, category, creditId);
            return ResponseEntity.ok("Archivo actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> DeletePdfFile(@PathVariable Long id) throws Exception{
        var isdeleted = pdfFileservice.deletePdfFile(id);
        return ResponseEntity.noContent().build();
    }

}
