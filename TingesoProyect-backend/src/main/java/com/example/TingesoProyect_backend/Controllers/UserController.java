package com.example.TingesoProyect_backend.Controllers;

import com.example.TingesoProyect_backend.Entities.User;
import com.example.TingesoProyect_backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestabanco/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> ListUserNotRegister(){
        List<User> Users = userService.getAllUserNotRegister();
        return ResponseEntity.ok(Users);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<User>> ListUserRegister(){
        List<User> Users = userService.getAllUserRegister();
        return ResponseEntity.ok(Users);
    }

    @GetMapping("/rut")
    public ResponseEntity<User> GetUserByRut(@RequestParam String rut){
        User user = userService.getUserbyRut(rut);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<User> SaveUser(@RequestBody User user){
        User newUser = userService.saveUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/")
    public ResponseEntity<User> UpdateUser(@RequestBody User user){
        User newUser = userService.updateUser(user);
        return ResponseEntity.ok(newUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> DeleteUserId(@PathVariable Long id) throws Exception {
        var isdeleted = userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
