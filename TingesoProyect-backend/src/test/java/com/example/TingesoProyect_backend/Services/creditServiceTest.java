package com.example.TingesoProyect_backend.Services;

import com.example.TingesoProyect_backend.Entities.User;
import com.example.TingesoProyect_backend.Entities.credit;
import com.example.TingesoProyect_backend.Repositories.CreditRepository;
import com.example.TingesoProyect_backend.Repositories.UserRepository;
import com.example.TingesoProyect_backend.util.CreditStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class creditServiceTest{
    @Mock
    CreditRepository creditRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    creditService creditService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCreditProcess() {
        // Datos de prueba
        credit credit1 = new credit(1L, "12345678-9", new Date(), 500000, 80.0, 5.0, 12, "comment1", true, 1, false, null, false, CreditStatus.EN_REVISION_INICIAL);
        credit credit2 = new credit(2L, "98765432-1", new Date(), 800000, 90.0, 4.5, 24, "comment2", true, 2, true, null, true, CreditStatus.PENDIENTE_DE_DOCUMENTACION);
        ArrayList<credit> creditsInProcess = new ArrayList<>(Arrays.asList(credit1, credit2));

        // Configuración del mock
        when(creditRepository.findByProcessCredit(true)).thenReturn(creditsInProcess);

        // Llamada al método y verificación del resultado
        ArrayList<credit> result = creditService.getAllCreditProcess();

        assertEquals(creditsInProcess, result, "Debe devolver solo los créditos en proceso");
        verify(creditRepository, times(1)).findByProcessCredit(true);
    }

    @Test
    void testGetCreditByRut_ValidRut() {
        // Arrange: Create a valid user and credits
        String validRut = "12345678-9";
        User user = new User();
        user.setRut(validRut);

        credit credit1 = new credit(1L, validRut, new Date(), 500000, 80.0, 5.0, 12, "comment1", true, 1, false, null, false, CreditStatus.EN_REVISION_INICIAL);
        credit credit2 = new credit(2L, validRut, new Date(), 800000, 90.0, 4.5, 24, "comment2", true, 2, true, null, true, CreditStatus.PENDIENTE_DE_DOCUMENTACION);
        ArrayList<credit> expectedCredits = new ArrayList<>(Arrays.asList(credit1, credit2));

        // Configure mocks
        when(userRepository.findByRut(validRut)).thenReturn(user);
        when(creditRepository.findByRutClient(validRut)).thenReturn(expectedCredits);

        // Act: Call the method
        ArrayList<credit> result = creditService.getCreditByRut(validRut);

        // Assert: Verify the result
        assertEquals(expectedCredits, result, "The list of credits should match the expected list");
    }

    @Test
    void testGetCreditByRut_NullRut() {
        // Act & Assert: Expect IllegalArgumentException when `rutClient` is null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            creditService.getCreditByRut(null);
        });
        assertEquals("por favor ingrese un rut", exception.getMessage());
    }

    @Test
    void testGetCreditByRut_UnregisteredUser() {
        // Arrange: `rutClient` does not correspond to any registered user
        String unregisteredRut = "98765432-1";

        // Configure mock
        when(userRepository.findByRut(unregisteredRut)).thenReturn(null);

        // Act & Assert: Expect IllegalArgumentException for unregistered user
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            creditService.getCreditByRut(unregisteredRut);
        });
        assertEquals("el usuario no esta registrado", exception.getMessage());
    }

    @Test
    void testGetCreditProcessByRut_ValidRut() {
        // Arrange: Set up a valid rut and corresponding credit
        String validRut = "12345678-9";
        User user = new User();
        user.setRut(validRut);

        credit creditProcess = new credit(1L, validRut, new Date(), 500000, 80.0, 5.0, 12, "comment1", true, 1, false, null, false, CreditStatus.EN_REVISION_INICIAL);
        Optional<credit> expectedCredit = Optional.of(creditProcess);

        // Configure mocks
        when(userRepository.findByRut(validRut)).thenReturn(user);
        when(creditRepository.findByRutClientAndProcessCredit(validRut, true)).thenReturn(expectedCredit);

        // Act: Call the method
        Optional<credit> result = creditService.getCreditProcessByRut(validRut);

        // Assert: Verify the result
        assertEquals(expectedCredit, result, "The credit should match the expected credit");
    }

    @Test
    void testGetCreditProcessByRut_NullRut() {
        // Act & Assert: Expect IllegalArgumentException when `rutClient` is null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            creditService.getCreditProcessByRut(null);
        });
        assertEquals("por favor ingrese un rut", exception.getMessage());
    }

    @Test
    void testGetCreditProcessByRut_UnregisteredUser() {
        // Arrange: `rutClient` does not correspond to any registered user
        String unregisteredRut = "98765432-1";

        // Configure mock
        when(userRepository.findByRut(unregisteredRut)).thenReturn(null);

        // Act & Assert: Expect IllegalArgumentException for unregistered user
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            creditService.getCreditProcessByRut(unregisteredRut);
        });
        assertEquals("el usuario no esta registrado", exception.getMessage());
    }

    @Test
    void testGetCreditProcessByRut_NoCreditProcessFound() {
        // Arrange: Set up a valid user but no ongoing credit process
        String validRut = "12345678-9";
        User user = new User();
        user.setRut(validRut);

        // Configure mocks
        when(userRepository.findByRut(validRut)).thenReturn(user);
        when(creditRepository.findByRutClientAndProcessCredit(validRut, true)).thenReturn(Optional.empty());

        // Act: Call the method
        Optional<credit> result = creditService.getCreditProcessByRut(validRut);

        // Assert: Verify that no credit process is found
        assertTrue(result.isEmpty(), "The result should be empty when no credit process is found");
    }

    @Test
    void testGetCreditById_CreditFound() {
        // Arrange: Set up a valid credit with ID
        long creditId = 1L;
        credit expectedCredit = new credit(creditId, "12345678-9", new Date(), 500000, 80.0, 5.0, 12, "comment1", true, 1, false, null, false, CreditStatus.EN_REVISION_INICIAL);

        // Configure mock to return the expected credit
        when(creditRepository.findById(creditId)).thenReturn(expectedCredit);

        // Act: Call the method
        credit result = creditService.getCreditById(creditId);

        // Assert: Verify that the returned credit matches the expected credit
        assertEquals(expectedCredit, result, "The credit should match the expected credit");
    }

    @Test
    void testGetCreditById_CreditNotFound() {
        // Arrange: Set up a non-existent credit ID
        long creditId = 2L;

        // Configure mock to return null when credit is not found
        when(creditRepository.findById(creditId)).thenReturn(null);

        // Act: Call the method
        credit result = creditService.getCreditById(creditId);

        // Assert: Verify that the result is null when credit is not found
        assertNull(result, "The result should be null when no credit is found with the given ID");
    }

    @Test
    void testSaveCredit_MissingRutClient() {
        credit newCredit = new credit();
        newCredit.setIdloanType(1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> creditService.saveCredit(newCredit));
        assertEquals("Por favor ingrese el RUT", exception.getMessage());
    }

    @Test
    void testSaveCredit_CreditInProcessExists() {
        credit newCredit = new credit();
        newCredit.setRutClient("12345678-9");
        newCredit.setIdloanType(1);

        when(creditRepository.findByRutClientAndProcessCredit("12345678-9", true)).thenReturn(Optional.of(new credit()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> creditService.saveCredit(newCredit));
        assertEquals("No puede solicitar otro prestamo ya que ya tiene uno en proceso", exception.getMessage());
    }

    @Test
    void testSaveCredit_MissingLoanType() {
        credit newCredit = new credit();
        newCredit.setRutClient("12345678-9");

        when(creditRepository.findByRutClientAndProcessCredit("12345678-9", true)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> creditService.saveCredit(newCredit));
        assertEquals("por favor seleccione un tipo de credito", exception.getMessage());
    }

    @Test
    void testSaveCredit_DuplicateLoanRequest() {
        credit newCredit = new credit();
        newCredit.setRutClient("12345678-9");
        newCredit.setIdloanType(1);

        credit approvedCredit = new credit();
        approvedCredit.setCreditStatus(CreditStatus.APROBADA);

        when(creditRepository.findByRutClientAndProcessCredit("12345678-9", true)).thenReturn(Optional.empty());
        when(creditRepository.findByRutClientAndIdloanType("12345678-9", 1)).thenReturn(Optional.of(approvedCredit));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> creditService.saveCredit(newCredit));
        assertEquals("No puede pedir el presente prestamo ya que solo se puede pedir una vez", exception.getMessage());
    }

    @Test
    void testSaveCredit_DuplicateLoanRequestPart2() {
        credit newCredit = new credit();
        newCredit.setRutClient("12345678-9");
        newCredit.setIdloanType(2);

        credit approvedCredit = new credit();
        approvedCredit.setCreditStatus(CreditStatus.APROBADA);

        when(creditRepository.findByRutClientAndProcessCredit("12345678-9", true)).thenReturn(Optional.empty());
        when(creditRepository.findByRutClientAndIdloanType("12345678-9", 2)).thenReturn(Optional.of(approvedCredit));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> creditService.saveCredit(newCredit));
        assertEquals("No puede pedir el presente prestamo ya que solo se puede pedir una vez", exception.getMessage());
    }

    @Test
    void testSaveCredit_UserNotRegistered() {
        credit newCredit = new credit();
        newCredit.setRutClient("12345678-9");
        newCredit.setIdloanType(1);

        when(creditRepository.findByRutClientAndProcessCredit("12345678-9", true)).thenReturn(Optional.empty());
        when(userRepository.findByRut("12345678-9")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> creditService.saveCredit(newCredit));
        assertEquals("No esta registrado este rut de usuario, por favor registrese primero antes de solicitar un prestamo", exception.getMessage());
    }

    @Test
    void testSaveCredit_UserRegistrationNotConfirmed() {
        credit newCredit = new credit();
        newCredit.setRutClient("12345678-9");
        newCredit.setIdloanType(1);

        User unconfirmedUser = new User();
        unconfirmedUser.setRegister(false);

        when(creditRepository.findByRutClientAndProcessCredit("12345678-9", true)).thenReturn(Optional.empty());
        when(userRepository.findByRut("12345678-9")).thenReturn(unconfirmedUser);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> creditService.saveCredit(newCredit));
        assertEquals("Su registro aun no ha sido confirmado, por favor intente de nuevo mas tarde", exception.getMessage());
    }

    @Test
    void testSaveCredit_Success() {
        credit newCredit = new credit();
        newCredit.setRutClient("12345678-9");
        newCredit.setIdloanType(1);

        User confirmedUser = new User();
        confirmedUser.setRegister(true);

        when(creditRepository.findByRutClientAndProcessCredit("12345678-9", true)).thenReturn(Optional.empty());
        when(creditRepository.findByRutClientAndIdloanType("12345678-9", 1)).thenReturn(Optional.empty());
        when(userRepository.findByRut("12345678-9")).thenReturn(confirmedUser);
        when(creditRepository.save(newCredit)).thenReturn(newCredit);

        credit savedCredit = creditService.saveCredit(newCredit);

        assertEquals(newCredit, savedCredit, "The saved credit should match the new credit");
    }

    @Test
    void testUpdateCredit_MissingRutClient() {
        credit updateCredit = new credit();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> creditService.updateCredit(updateCredit));
        assertEquals("Por favor ingrese el RUT", exception.getMessage());
    }

    @Test
    void testUpdateCredit_UserNotRegistered() {
        credit updateCredit = new credit();
        updateCredit.setRutClient("12345678-9");

        when(userRepository.findByRut("12345678-9")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> creditService.updateCredit(updateCredit));
        assertEquals("No esta registrado este rut de usuario, por favor registrese primero antes de solicitar un prestamo", exception.getMessage());
    }

    @Test
    void testUpdateCredit_UserRegistrationNotConfirmed() {
        credit updateCredit = new credit();
        updateCredit.setRutClient("12345678-9");

        User unconfirmedUser = new User();
        unconfirmedUser.setRegister(false);

        when(userRepository.findByRut("12345678-9")).thenReturn(unconfirmedUser);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> creditService.updateCredit(updateCredit));
        assertEquals("Su registro aun no ha sido confirmado, por favor intente de nuevo mas tarde", exception.getMessage());
    }

    @Test
    void testUpdateCredit_Success() {
        credit updateCredit = new credit();
        updateCredit.setRutClient("12345678-9");

        User confirmedUser = new User();
        confirmedUser.setRegister(true);

        when(userRepository.findByRut("12345678-9")).thenReturn(confirmedUser);
        when(creditRepository.save(updateCredit)).thenReturn(updateCredit);

        credit result = creditService.updateCredit(updateCredit);

        assertEquals(updateCredit, result, "The updated credit should match the input credit");
    }

    @Test
    void testDeleteCredit_Success() throws Exception {
        Long creditId = 1L;

        doNothing().when(creditRepository).deleteById(creditId);

        boolean result = creditService.deleteCredit(creditId);

        assertTrue(result, "Credit deletion should return true on success");
        verify(creditRepository, times(1)).deleteById(creditId);
    }

    @Test
    void testDeleteCredit_ExceptionThrown() {
        Long creditId = 1L;

        doThrow(new RuntimeException("Deletion error")).when(creditRepository).deleteById(creditId);

        Exception exception = assertThrows(Exception.class, () -> creditService.deleteCredit(creditId));
        assertEquals("Deletion error", exception.getMessage(), "Exception message should match the thrown exception");

        verify(creditRepository, times(1)).deleteById(creditId);
    }
}
