package com.example.TingesoProyect_backend.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CalculateServiceTest {

    @InjectMocks
    CalculateService calculateService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSimulatorCalculate_ValidInputs() {
        // Arrange
        Double capitalAmount = 100000.0;
        Double annualInterest = 5.0;
        Integer years = 10;

        // Act
        Integer result = calculateService.SimulatorCalculate(capitalAmount, annualInterest, years);

        // Assert
        assertNotNull(result);
        assertTrue(result > 0);
    }

    @Test
    void testSimulatorCalculate_ZeroOrNegativeInputs() {
        // Arrange
        Double capitalAmount = -100000.0;
        Double annualInterest = 5.0;
        Integer years = 10;

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                calculateService.SimulatorCalculate(capitalAmount, annualInterest, years));
        assertEquals("por favor ingrese valores en los parametros que sea mayor que 0", exception.getMessage());
    }

    @Test
    void testCostTotal_ValidInputs() {
        // Arrange
        Double monthlyPayment = 50000.0;
        Double annualInterest = 4.5;
        Integer years = 15;

        // Act
        Integer result = calculateService.CostTotal(monthlyPayment, annualInterest, years);

        // Assert
        assertNotNull(result);
        assertTrue(result > 0);
    }

    @Test
    void testCostTotal_ZeroOrNegativeInputs() {
        // Arrange
        Double monthlyPayment = 0.0;
        Double annualInterest = 4.5;
        Integer years = 15;

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                calculateService.CostTotal(monthlyPayment, annualInterest, years));
        assertEquals("por favor ingrese valores en los parametros que sea distinto o mayor que 0", exception.getMessage());
    }

    @Test
    void testRelacionCuotaIngreso_ValidInputs() {
        Double cuotaMensual = 50000.0;
        Double ingreso = 200000.0;

        double result = calculateService.RelacionCuotaIngreso(cuotaMensual, ingreso);

        assertEquals(25.0, result);
    }

    @Test
    void testRelacionCuotaIngreso_ZeroIngreso() {
        Double cuotaMensual = 50000.0;
        Double ingreso = 0.0;

        double result = calculateService.RelacionCuotaIngreso(cuotaMensual, ingreso);

        assertEquals(0.0, result);
    }

    @Test
    void testRelacionCuotaIngreso_InvalidCuotaMensual() {
        Double cuotaMensual = -50000.0;
        Double ingreso = 200000.0;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                calculateService.RelacionCuotaIngreso(cuotaMensual, ingreso));
        assertEquals("por favor ingrese la cuota mensual", exception.getMessage());
    }

    @Test
    void testRelacionDeudaIngreso_ValidInputs() {
        Double cuotaMensual = 50000.0;
        Integer MontoDeudas = 100000;
        Integer ingreso = 400000;

        double result = calculateService.RelacionDeudaIngreso(cuotaMensual, MontoDeudas, ingreso);

        assertEquals(37.5, result);
    }

    @Test
    void testRelacionDeudaIngreso_InvalidInputs() {
        Double cuotaMensual = -50000.0;
        Integer ingreso = 400000;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                calculateService.RelacionDeudaIngreso(cuotaMensual, 100000, ingreso));
        assertEquals("por favor ingrese un valor en mayor a cero en la cuota mensial o ingreso", exception.getMessage());
    }

    @Test
    void testYearSolicited_ValidInputs() {
        LocalDate birthDate = LocalDate.now().minusYears(30);
        Date birthDateAsDate = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Boolean result = calculateService.yearSolicited(birthDateAsDate, 10);

        assertTrue(result);
    }

    @Test
    void testYearSolicited_InvalidBirthdate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                calculateService.yearSolicited(null, 10));
        assertEquals("Por favor ingrese la fecha de nacimiento.", exception.getMessage());
    }

    @Test
    void testYearSolicited_InValidTimeLoan() {
        LocalDate birthDate = LocalDate.now().minusYears(30);
        Date birthDateAsDate = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                calculateService.yearSolicited(birthDateAsDate,null));
        assertEquals("Por favor, ingrese el tiempo del préstamo.",exception.getMessage());
    }

    @Test
    void testMinPaySaving_ValidInputs() {
        Integer monthPay = 500000;
        Integer monthSaving = 60000;

        Boolean result = calculateService.minPaySaving(monthPay, monthSaving);

        assertTrue(result);
    }

    @Test
    void testMinPaySaving_InvalidInputs() {
        Integer monthPay = 0;
        Integer monthSaving = 60000;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                calculateService.minPaySaving(monthPay, monthSaving));
        assertEquals("por favor, ingrese el monto del prestamo o el monto de la cuenta de ahorro", exception.getMessage());
    }

    @Test
    void testRelationMonthSavingYears_ValidInputs_LessThanTwoYears() {
        Integer monthPay = 500000;
        Integer monthSaving = 120000;
        Integer years = 1;

        Boolean result = calculateService.RelationMonthSavingYears(monthPay, monthSaving, years);

        assertTrue(result);
    }

    @Test
    void testRelationMonthSavingYears_ValidInputs_MoreThanTwoYears() {
        Integer monthPay = 500000;
        Integer monthSaving = 60000;
        Integer years = 3;

        Boolean result = calculateService.RelationMonthSavingYears(monthPay, monthSaving, years);

        assertTrue(result);
    }

    @Test
    void testRelationMonthSavingYears_InvalidInputs() {
        Integer monthPay = 500000;
        Integer monthSaving = null;
        Integer years = 3;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                calculateService.RelationMonthSavingYears(monthPay, monthSaving, years));
        assertEquals("por favor, ingrese el monto de la cuenta de ahorro o el monto del prestamos o los años de antiguedad", exception.getMessage());
    }

}
