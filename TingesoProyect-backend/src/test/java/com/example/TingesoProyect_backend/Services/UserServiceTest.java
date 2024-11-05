package com.example.TingesoProyect_backend.Services;

import com.example.TingesoProyect_backend.Entities.User;
import com.example.TingesoProyect_backend.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsAdult_True() {
        // Fecha de nacimiento hace más de 18 años
        LocalDate pastDate = LocalDate.now().minusYears(20);
        Date birthdate = Date.from(pastDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Verificar que el resultado sea true
        assertTrue(userService.isAdult(birthdate), "La persona debe ser considerada adulta");
    }

    @Test
    public void testIsAdult_False() {
        // Fecha de nacimiento hace menos de 18 años
        LocalDate recentDate = LocalDate.now().minusYears(17);
        Date birthdate = Date.from(recentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Verificar que el resultado sea false
        assertFalse(userService.isAdult(birthdate), "La persona no debe ser considerada adulta");
    }

    @Test
    public void testIsAdult_ExactBoundary() {
        // Fecha de nacimiento hace exactamente 18 años
        LocalDate boundaryDate = LocalDate.now().minusYears(18);
        Date birthdate = Date.from(boundaryDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Verificar que el resultado sea true
        assertTrue(userService.isAdult(birthdate), "La persona debe ser considerada adulta exactamente a los 18 años");
    }

    @Test
    public void testGetAllUserNotRegister() {
        // Crear usuarios donde algunos no están registrados
        User user1 = new User(1L, "12345678-9", "Juan", "Pérez", new Date(), 500000, false);
        User user2 = new User(2L, "98765432-1", "Ana", "Gómez", new Date(), 700000, true);
        User user3 = new User(3L, "11223344-5", "Luis", "Martínez", new Date(), 800000, false);

        // Lista de usuarios no registrados esperada
        ArrayList<User> usersNotRegistered = new ArrayList<>(Arrays.asList(user1, user3));

        // Configurar el mock para devolver usuarios no registrados
        when(userRepository.findByRegister(false)).thenReturn(usersNotRegistered);

        // Ejecutar la función y verificar el resultado
        ArrayList<User> result = userService.getAllUserNotRegister();
        assertEquals(usersNotRegistered, result, "Debe devolver solo los usuarios no registrados");
    }

    @Test
    public void testGetAllUserRegister() {
        // Crear usuarios donde algunos están registrados
        User user1 = new User(1L, "12345678-9", "Juan", "Pérez", new Date(), 500000, true);
        User user2 = new User(2L, "98765432-1", "Ana", "Gómez", new Date(), 700000, true);
        User user3 = new User(3L, "11223344-5", "Luis", "Martínez", new Date(), 800000, false);

        // Lista de usuarios registrados esperada
        ArrayList<User> usersRegistered = new ArrayList<>(Arrays.asList(user1, user2));

        // Configurar el mock para devolver usuarios registrados
        when(userRepository.findByRegister(true)).thenReturn(usersRegistered);

        // Ejecutar la función y verificar el resultado
        ArrayList<User> result = userService.getAllUserRegister();
        assertEquals(usersRegistered, result, "Debe devolver solo los usuarios registrados");
    }

    @Test
    public void testGetUserbyRut() {
        // Crear un usuario de ejemplo
        User expectedUser = new User(1L, "12345678-9", "Juan", "Pérez", new Date(), 500000, false);

        // Configurar el mock para devolver el usuario por RUT
        when(userRepository.findByRut("12345678-9")).thenReturn(expectedUser);

        // Ejecutar la función y verificar el resultado
        User result = userService.getUserbyRut("12345678-9");
        assertEquals(expectedUser, result, "Debe devolver el usuario correspondiente al RUT proporcionado");
    }

    @Test
    public void testGetUserbyRut_NullInput() {
        // Ejecutar la función con RUT nulo
        User result = userService.getUserbyRut(null);
        assertNull(result, "Debe devolver null si el RUT es nulo");
    }

    @Test
    public void testSaveUser_NullRut() {
        User user = new User();
        user.setName("Juan");
        user.setLastname("Pérez");
        user.setBirthdate(new Date());

        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.saveUser(user);
        });

        assertEquals("Por favor ingrese un RUT", exception.getMessage());
    }

    @Test
    public void testSaveUser_NullName() {
        User user = new User();
        user.setRut("12345678-9");
        user.setLastname("Pérez");
        user.setBirthdate(new Date());

        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.saveUser(user);
        });

        assertEquals("Por favor ingrese su nombre", exception.getMessage());
    }

    @Test
    public void testSaveUser_NullLastname() {
        User user = new User();
        user.setRut("12345678-9");
        user.setName("Juan");
        user.setBirthdate(new Date());

        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.saveUser(user);
        });

        assertEquals("por favor ingrese su apellido", exception.getMessage());
    }

    @Test
    public void testSaveUser_NullBirthdate() {
        User user = new User();
        user.setRut("12345678-9");
        user.setName("Juan");
        user.setLastname("Pérez");

        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.saveUser(user);
        });

        assertEquals("por favor ingrese su fecha de nacimiento", exception.getMessage());
    }

    @Test
    public void testSaveUser_UnderageUser() {
        // Crear una fecha de nacimiento que represente a un usuario menor de 18 años
        // Por ejemplo, un usuario que nació hace 17 años
        LocalDate birthdate = LocalDate.now().minusYears(17).minusDays(1); // 17 años y 1 día
        User user = new User(1L, "12345678-9", "Juan", "Pérez", Date.from(birthdate.atStartOfDay(ZoneId.systemDefault()).toInstant()), 500000, false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(user);
        });

        assertEquals("el usuario debe tener al menos 18 años", exception.getMessage());
    }

    @Test
    public void testSaveUser_ValidUser() {
        User user = new User(1L, "12345678-9", "Juan", "Pérez", new Date(System.currentTimeMillis() - 20L * 365 * 24 * 60 * 60 * 1000), 500000, false);

        // Configurar el mock para que el usuario se guarde correctamente
        when(userRepository.save(user)).thenReturn(user);

        // Ejecutar el método y verificar el resultado
        User result = userService.saveUser(user);
        assertEquals(user, result, "El usuario guardado debe ser el mismo que se pasó como argumento");
    }

    @Test
    public void testUpdateUser_ValidUser() {
        // Crear un usuario válido
        LocalDate birthdate = LocalDate.now().minusYears(18); // 18 años
        User user = new User(1L, "12345678-9", "Juan", "Pérez", Date.from(birthdate.atStartOfDay(ZoneId.systemDefault()).toInstant()), 500000, false);

        // Configurar el mock para devolver el usuario actualizado
        when(userRepository.save(user)).thenReturn(user);

        // Ejecutar la función y verificar el resultado
        User result = userService.updateUser(user);
        assertEquals(user, result, "Debería devolver el usuario actualizado");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser_UnderageUser() {
        // Crear un usuario menor de edad (menos de 18 años)
        LocalDate birthdate = LocalDate.now().minusYears(17).minusDays(1); // 17 años y 1 día
        User user = new User(1L, "12345678-9", "Juan", "Pérez", Date.from(birthdate.atStartOfDay(ZoneId.systemDefault()).toInstant()), 500000, false);

        // Probar que se lanza la excepción al intentar actualizar
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(user);
        });

        assertEquals("el usuario debe tener al menos 18 años", exception.getMessage());
    }

    @Test
    public void testUpdateUser_NullRut() {
        // Crear un usuario con RUT nulo
        User user = new User(1L, null, "Juan", "Pérez", new Date(), 500000, false);

        // Probar que se lanza la excepción al intentar actualizar
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.updateUser(user);
        });

        assertEquals("Por favor ingrese un RUT", exception.getMessage());
    }

    @Test
    public void testUpdateUser_NullName() {
        // Crear un usuario con nombre nulo
        User user = new User(1L, "12345678-9", null, "Pérez", new Date(), 500000, false);

        // Probar que se lanza la excepción al intentar actualizar
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.updateUser(user);
        });

        assertEquals("Por favor ingrese su nombre", exception.getMessage());
    }

    @Test
    public void testUpdateUser_NullLastname() {
        // Crear un usuario con apellido nulo
        User user = new User(1L, "12345678-9", "Juan", null, new Date(), 500000, false);

        // Probar que se lanza la excepción al intentar actualizar
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.updateUser(user);
        });

        assertEquals("por favor ingrese su apellido", exception.getMessage());
    }

    @Test
    public void testUpdateUser_NullBirthdate() {
        // Crear un usuario con fecha de nacimiento nula
        User user = new User(1L, "12345678-9", "Juan", "Pérez", null, 500000, false);

        // Probar que se lanza la excepción al intentar actualizar
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.updateUser(user);
        });

        assertEquals("por favor ingrese su fecha de nacimiento", exception.getMessage());
    }

    @Test
    public void testDeleteUser_ValidId() throws Exception {
        Long userId = 1L;

        // Ejecutar la función
        boolean result = userService.deleteUser(userId);

        // Verificar que el repositorio fue llamado correctamente
        verify(userRepository, times(1)).deleteById(userId);
        assertTrue(result, "Debería devolver true al eliminar el usuario");
    }

    @Test
    public void testDeleteUser_InvalidId() throws Exception {
        Long userId = 1L;

        // Configurar el mock para lanzar una excepción al intentar eliminar
        doThrow(new RuntimeException("Error al eliminar el usuario")).when(userRepository).deleteById(userId);

        // Probar que se lanza la excepción al intentar eliminar
        Exception exception = assertThrows(Exception.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("Error al eliminar el usuario", exception.getMessage());
    }

}
