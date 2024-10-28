package com.example.TingesoProyect_backend.Services;

import com.example.TingesoProyect_backend.Entities.User;
import com.example.TingesoProyect_backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    private boolean isAdult(Date birthdate){
        LocalDate birthLocalDate = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(birthLocalDate,LocalDate.now()).getYears() >= 18;
    }

    public ArrayList<User> getAllUserNotRegister(){
        return (ArrayList<User>) userRepository.findByRegister(false);
    }

    public ArrayList<User> getAllUserRegister(){ return (ArrayList<User>) userRepository.findByRegister(true);}

    public User getUserbyRut(String rut){
        return userRepository.findByRut(rut);
    }

    public User saveUser(User user){
        Objects.requireNonNull(user.getRut(), "Por favor ingrese un RUT");
        Objects.requireNonNull(user.getName(), "Por favor ingrese su nombre");
        Objects.requireNonNull(user.getLastname(), "por favor ingrese su apellido");
        Objects.requireNonNull(user.getBirthdate(), "por favor ingrese su fecha de nacimiento");

        if (!isAdult(user.getBirthdate())){
            throw new IllegalArgumentException("el usuario debe tener al menos 18 años");
        }

        return userRepository.save(user);
    }

    public User updateUser(User user){
        Objects.requireNonNull(user.getRut(), "Por favor ingrese un RUT");
        Objects.requireNonNull(user.getName(), "Por favor ingrese su nombre");
        Objects.requireNonNull(user.getLastname(), "por favor ingrese su apellido");
        Objects.requireNonNull(user.getBirthdate(), "por favor ingrese su fecha de nacimiento");

        if (!isAdult(user.getBirthdate())){
            throw new IllegalArgumentException("el usuario debe tener al menos 18 años");
        }

        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) throws Exception{
        try{
            userRepository.deleteById(id);
            return true;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
