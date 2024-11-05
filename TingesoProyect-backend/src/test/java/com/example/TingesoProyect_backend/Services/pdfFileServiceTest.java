package com.example.TingesoProyect_backend.Services;

import com.example.TingesoProyect_backend.Entities.pdfFile;
import com.example.TingesoProyect_backend.Repositories.pdfFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class pdfFileServiceTest {
    @Mock
    pdfFileRepository pdfFileRepository;

    @InjectMocks
    pdfFileService pdfFileService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetAllFiles() {
        ArrayList<pdfFile> pdfFiles = new ArrayList<>();
        pdfFiles.add(new pdfFile(1L, "Category1", "File1", new byte[]{}, 1L));
        pdfFiles.add(new pdfFile(2L, "Category2", "File2", new byte[]{}, 2L));

        when(pdfFileRepository.findAll()).thenReturn(pdfFiles);

        ArrayList<pdfFile> result = pdfFileService.getAllFiles();

        assertEquals(pdfFiles, result, "The returned list of files should match the expected list");
        verify(pdfFileRepository, times(1)).findAll();
    }

    @Test
    void testGetPdfFilesByCredit() {
        Long creditId = 1L;
        List<pdfFile> pdfFiles = List.of(
                new pdfFile(1L, "Category1", "File1", new byte[]{}, creditId),
                new pdfFile(2L, "Category2", "File2", new byte[]{}, creditId)
        );

        when(pdfFileRepository.findByCreditid(creditId)).thenReturn(pdfFiles);

        List<pdfFile> result = pdfFileService.getPdfFilesByCredit(creditId);

        assertEquals(pdfFiles, result, "The returned list of files should match the expected list for the credit ID");
        verify(pdfFileRepository, times(1)).findByCreditid(creditId);
    }

    @Test
    void testGetPdfFileByCategoryAndCredit() {
        String category = "Category1";
        Long creditId = 1L;

        pdfFile expectedPdfFile = new pdfFile(1L, category, "File1", new byte[]{}, creditId);

        when(pdfFileRepository.findByCategoryAndCreditid(category, creditId)).thenReturn(expectedPdfFile);

        pdfFile result = pdfFileService.getPdfFileByCategoryAndCredit(category, creditId);

        assertEquals(expectedPdfFile, result, "The returned pdfFile should match the expected pdfFile based on category and creditId");
        verify(pdfFileRepository, times(1)).findByCategoryAndCreditid(category, creditId);
    }

    @Test
    void testUpdateFile_FileExists() throws Exception {
        // Mock existing file and inputs
        Long id = 1L;
        MultipartFile file = new MockMultipartFile("file", "updated.pdf", "application/pdf", "updated data".getBytes());
        String category = "UpdatedCategory";
        Long creditId = 2L;

        pdfFile existingFile = new pdfFile(id, "OldFile", "OldCategory", "old data".getBytes(), creditId);
        pdfFile updatedFile = new pdfFile(id, file.getOriginalFilename(), category, file.getBytes(), creditId);

        when(pdfFileRepository.findById(id)).thenReturn(Optional.of(existingFile));
        when(pdfFileRepository.save(any(pdfFile.class))).thenReturn(updatedFile);

        // Execute the function
        pdfFile result = pdfFileService.updateFile(id, file, category, creditId);

        // Verify
        assertEquals(updatedFile.getName(), result.getName());
        assertEquals(updatedFile.getCategory(), result.getCategory());
        assertArrayEquals(updatedFile.getData(), result.getData());
        assertEquals(updatedFile.getCreditid(), result.getCreditid());
        verify(pdfFileRepository, times(1)).findById(id);
        verify(pdfFileRepository, times(1)).save(any(pdfFile.class));
    }

    @Test
    void testUpdateFile_FileNotFound() {
        // Mock inputs for a non-existing file
        Long id = 1L;
        MultipartFile file = new MockMultipartFile("file", "updated.pdf", "application/pdf", "updated data".getBytes());
        String category = "UpdatedCategory";
        Long creditId = 2L;

        when(pdfFileRepository.findById(id)).thenReturn(Optional.empty());

        // Execute and verify exception
        Exception exception = assertThrows(Exception.class, () -> {
            pdfFileService.updateFile(id, file, category, creditId);
        });

        assertEquals("Archivo no encontrado", exception.getMessage());
        verify(pdfFileRepository, times(1)).findById(id);
        verify(pdfFileRepository, never()).save(any(pdfFile.class));
    }

    @Test
    void testStoreFile() throws IOException {
        // Mock file and inputs
        MultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "dummy data".getBytes());
        String category = "Category1";
        Long creditId = 1L;

        pdfFile savedFile = new pdfFile();
        savedFile.setName(file.getOriginalFilename());
        savedFile.setCategory(category);
        savedFile.setData(file.getBytes());
        savedFile.setCreditid(creditId);

        when(pdfFileRepository.save(any(pdfFile.class))).thenReturn(savedFile);

        // Execute the function
        pdfFile result = pdfFileService.StoreFile(file, category, creditId);

        // Verify
        assertEquals(savedFile.getName(), result.getName());
        assertEquals(savedFile.getCategory(), result.getCategory());
        assertArrayEquals(savedFile.getData(), result.getData());
        assertEquals(savedFile.getCreditid(), result.getCreditid());
        verify(pdfFileRepository, times(1)).save(any(pdfFile.class));
    }

    @Test
    void testDeletePdfFile_Success() throws Exception {
        // Arrange
        Long id = 1L;

        // Act
        boolean result = pdfFileService.deletePdfFile(id);

        // Assert
        assertTrue(result);
        verify(pdfFileRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeletePdfFile_ExceptionThrown() {
        // Arrange
        Long id = 1L;
        doThrow(new RuntimeException("Deletion failed")).when(pdfFileRepository).deleteById(id);

        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> pdfFileService.deletePdfFile(id));
        assertEquals("Deletion failed", exception.getMessage());
        verify(pdfFileRepository, times(1)).deleteById(id);
    }
}
