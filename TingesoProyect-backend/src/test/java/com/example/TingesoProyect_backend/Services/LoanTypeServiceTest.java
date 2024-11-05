package com.example.TingesoProyect_backend.Services;

import com.example.TingesoProyect_backend.Entities.LoanType;
import com.example.TingesoProyect_backend.Repositories.LoanTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanTypeServiceTest {
    @Mock
    private LoanTypeRepository loanTypeRepository;

    @InjectMocks
    private LoanTypeService loanTypeService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllLoanType() {
        // Crear algunos tipos de préstamo de ejemplo
        LoanType loanType1 = new LoanType(1L, "Personal Loan", 5, 1.5, 2.5, 90.0, Arrays.asList("Requirement 1", "Requirement 2"));
        LoanType loanType2 = new LoanType(2L, "Home Loan", 15, 1.2, 2.0, 80.0, Arrays.asList("Requirement A", "Requirement B"));

        // Configurar el mock para devolver los tipos de préstamo como una lista
        List<LoanType> loanTypeList = Arrays.asList(loanType1, loanType2);
        when(loanTypeRepository.findAll()).thenReturn(loanTypeList); // No hay cambios aquí

        // Ejecutar la función
        ArrayList<LoanType> result = new ArrayList<>(loanTypeService.getAllLoanType());

        // Verificar que el repositorio fue llamado correctamente
        verify(loanTypeRepository, times(1)).findAll();

        // Verificar que el resultado es el esperado
        assertEquals(2, result.size(), "Debe devolver dos tipos de préstamo");
        assertEquals(loanType1, result.get(0), "El primer tipo de préstamo debe ser 'Personal Loan'");
        assertEquals(loanType2, result.get(1), "El segundo tipo de préstamo debe ser 'Home Loan'");
    }

    @Test
    public void testGetLoanTypebyName() {
        // Crear un tipo de préstamo de ejemplo
        String loanName = "Personal Loan";
        LoanType expectedLoanType = new LoanType(1L, loanName, 5, 1.5, 2.5, 90.0, Arrays.asList("Requirement 1", "Requirement 2"));

        // Configurar el mock para devolver el tipo de préstamo cuando se busca por nombre
        when(loanTypeRepository.findByNameLoan(loanName)).thenReturn(expectedLoanType);

        // Ejecutar la función
        LoanType result = loanTypeService.getLoanTypebyName(loanName);

        // Verificar que el repositorio fue llamado correctamente
        verify(loanTypeRepository, times(1)).findByNameLoan(loanName);

        // Verificar que el resultado es el esperado
        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(expectedLoanType, result, "Debe devolver el tipo de préstamo correcto");
    }

    @Test
    public void testGetLoanTypebyName_NotFound() {
        // Configurar el mock para devolver null cuando no se encuentra el tipo de préstamo
        String loanName = "Nonexistent Loan";
        when(loanTypeRepository.findByNameLoan(loanName)).thenReturn(null);

        // Ejecutar la función
        LoanType result = loanTypeService.getLoanTypebyName(loanName);

        // Verificar que el repositorio fue llamado correctamente
        verify(loanTypeRepository, times(1)).findByNameLoan(loanName);

        // Verificar que el resultado es nulo cuando no se encuentra el tipo de préstamo
        assertNull(result, "Debe devolver null cuando no se encuentra el tipo de préstamo");
    }

    @Test
    public void testGetLoanTypeById_Found() {
        // Crear un tipo de préstamo de ejemplo
        long loanId = 1L;
        LoanType expectedLoanType = new LoanType(loanId, "Personal Loan", 5, 1.5, 2.5, 90.0, Arrays.asList("Requirement 1", "Requirement 2"));

        // Configurar el mock para devolver el tipo de préstamo cuando se busca por ID
        when(loanTypeRepository.findById(loanId)).thenReturn(expectedLoanType);

        // Ejecutar la función
        LoanType result = loanTypeService.getLoanTypeById(loanId);

        // Verificar que el repositorio fue llamado correctamente
        verify(loanTypeRepository, times(1)).findById(loanId);

        // Verificar que el resultado es el esperado
        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(expectedLoanType, result, "Debe devolver el tipo de préstamo correcto");
    }

    @Test
    public void testGetLoanTypeById_NotFound() {
        // Configurar el mock para devolver null cuando no se encuentra el tipo de préstamo
        long loanId = 2L;
        when(loanTypeRepository.findById(loanId)).thenReturn(null);

        // Ejecutar la función
        LoanType result = loanTypeService.getLoanTypeById(loanId);

        // Verificar que el repositorio fue llamado correctamente
        verify(loanTypeRepository, times(1)).findById(loanId);

        // Verificar que el resultado es nulo cuando no se encuentra el tipo de préstamo
        assertNull(result, "Debe devolver null cuando no se encuentra el tipo de préstamo");
    }

    @Test
    public void testSaveLoanType_Success() {
        LoanType loanType = new LoanType(0, "Auto Loan", 10, 1.2, 2.5, 80.0, Arrays.asList("Requirement A", "Requirement B"));

        when(loanTypeRepository.save(loanType)).thenReturn(loanType);

        LoanType result = loanTypeService.saveLoanType(loanType);

        verify(loanTypeRepository, times(1)).save(loanType);
        assertEquals(loanType, result, "Debe guardar y devolver el tipo de préstamo");
    }

    @Test
    public void testSaveLoanType_NullNameLoan() {
        LoanType loanType = new LoanType(0, null, 10, 1.2, 2.5, 80.0, Arrays.asList("Requirement A"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> loanTypeService.saveLoanType(loanType));
        assertEquals("por favor ingrese un nombre del prestamo", exception.getMessage());
    }

    @Test
    public void testSaveLoanType_EmptyRequirements() {
        LoanType loanType = new LoanType(0, "Auto Loan", 10, 1.2, 2.5, 80.0, Arrays.asList());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> loanTypeService.saveLoanType(loanType));
        assertEquals("por favor ingrese los requerimientos necesarios", exception.getMessage());
    }

    @Test
    public void testSaveLoanType_InvalidMaxTime() {
        LoanType loanType = new LoanType(0, "Auto Loan", 0, 1.2, 2.5, 80.0, Arrays.asList("Requirement A"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> loanTypeService.saveLoanType(loanType));
        assertEquals("por favor ingrese el año de duracion del prestamo", exception.getMessage());
    }

    @Test
    public void testSaveLoanType_InvalidMaxInterest() {
        LoanType loanType = new LoanType(0, "Auto Loan", 10, 1.2, 0.0, 80.0, Arrays.asList("Requirement A"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> loanTypeService.saveLoanType(loanType));
        assertEquals("por favor ingrese el interes maximo del prestamo", exception.getMessage());
    }

    @Test
    public void testSaveLoanType_InvalidMinInterest() {
        LoanType loanType = new LoanType(0, "Auto Loan", 10, 0.0, 2.5, 80.0, Arrays.asList("Requirement A"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> loanTypeService.saveLoanType(loanType));
        assertEquals("por favor ingrese el interes minimo del prestamo", exception.getMessage());
    }

    @Test
    public void testSaveLoanType_InvalidMaxFinanPorcent() {
        LoanType loanType = new LoanType(0, "Auto Loan", 10, 1.2, 2.5, 0.0, Arrays.asList("Requirement A"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> loanTypeService.saveLoanType(loanType));
        assertEquals("porfavor ingrese el porcentaje maximo de financiamiento mayor a 0", exception.getMessage());
    }

    @Test
    public void testUpdateLoanType_Success() {
        LoanType loanType = new LoanType(1L, "Personal Loan", 5, 1.5, 2.5, 90.0, Arrays.asList("Requirement 1", "Requirement 2"));

        when(loanTypeRepository.save(loanType)).thenReturn(loanType);

        LoanType result = loanTypeService.updateLoanType(loanType);

        verify(loanTypeRepository, times(1)).save(loanType);
        assertEquals(loanType, result, "Debe actualizar y devolver el tipo de préstamo");
    }

    @Test
    public void testUpdateLoanType_NullNameLoan() {
        LoanType loanType = new LoanType(1L, null, 5, 1.5, 2.5, 90.0, Arrays.asList("Requirement 1"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> loanTypeService.updateLoanType(loanType));
        assertEquals("por favor ingrese un nombre del prestamo", exception.getMessage());
    }

    @Test
    public void testUpdateLoanType_EmptyRequirements() {
        LoanType loanType = new LoanType(1L, "Personal Loan", 5, 1.5, 2.5, 90.0, Arrays.asList());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> loanTypeService.updateLoanType(loanType));
        assertEquals("por favor ingrese los requerimientos necesarios", exception.getMessage());
    }

    @Test
    public void testUpdateLoanType_InvalidMaxTime() {
        LoanType loanType = new LoanType(1L, "Personal Loan", 0, 1.5, 2.5, 90.0, Arrays.asList("Requirement 1"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> loanTypeService.updateLoanType(loanType));
        assertEquals("por favor ingrese el año de duracion del prestamo", exception.getMessage());
    }

    @Test
    public void testUpdateLoanType_InvalidMaxInterest() {
        LoanType loanType = new LoanType(1L, "Personal Loan", 5, 1.5, 0.0, 90.0, Arrays.asList("Requirement 1"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> loanTypeService.updateLoanType(loanType));
        assertEquals("por favor ingrese el interes maximo del prestamo", exception.getMessage());
    }

    @Test
    public void testUpdateLoanType_InvalidMinInterest() {
        LoanType loanType = new LoanType(1L, "Personal Loan", 5, 0.0, 2.5, 90.0, Arrays.asList("Requirement 1"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> loanTypeService.updateLoanType(loanType));
        assertEquals("por favor ingrese el interes minimo del prestamo", exception.getMessage());
    }

    @Test
    public void testUpdateLoanType_InvalidMaxFinanPorcent() {
        LoanType loanType = new LoanType(1L, "Personal Loan", 5, 1.5, 2.5, 0.0, Arrays.asList("Requirement 1"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> loanTypeService.updateLoanType(loanType));
        assertEquals("porfavor ingrese el porcentaje maximo de financiamiento mayor a 0", exception.getMessage());
    }

    @Test
    public void testDeleteLoanType_Success() throws Exception {
        Long loanTypeId = 1L;

        doNothing().when(loanTypeRepository).deleteById(loanTypeId);

        boolean result = loanTypeService.deleteLoanType(loanTypeId);

        verify(loanTypeRepository, times(1)).deleteById(loanTypeId);
        assertTrue(result, "El método debería devolver true si la eliminación fue exitosa.");
    }

    @Test
    public void testDeleteLoanType_ExceptionThrown() {
        Long loanTypeId = 1L;

        doThrow(new RuntimeException("Error al eliminar")).when(loanTypeRepository).deleteById(loanTypeId);

        Exception exception = assertThrows(Exception.class, () -> loanTypeService.deleteLoanType(loanTypeId));

        assertEquals("Error al eliminar", exception.getMessage());
        verify(loanTypeRepository, times(1)).deleteById(loanTypeId);
    }

}
