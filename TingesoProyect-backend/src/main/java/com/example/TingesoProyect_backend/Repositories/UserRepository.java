package com.example.TingesoProyect_backend.Repositories;

import com.example.TingesoProyect_backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    public User findByRut(String Rut);

    public List<User> findByRegister(Boolean register);

    public User findById(long id);
}
