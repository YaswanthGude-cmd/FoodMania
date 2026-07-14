package com.example.demo.controllers;


import com.example.demo.dto.AdminStatsDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.models.Users;
import com.example.demo.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UsersService usersService;

    @Autowired
    public UserController(UsersService users){
        this.usersService = users;
    }

    @GetMapping("/all")
    public List<Users> getAllUsers(){
        return usersService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<HashMap<String , String>> createUser(@RequestBody Users users){
        try{
            usersService.createUser(users);
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                   "status", "1",
                   "message","User has created Successfully"
            )));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status", "0",
                    "message",e.getMessage()
            )));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody Users users){
        try{
            Users updatedUser = usersService.updateUser(users);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                    "status", "0",
                    "message",e.getMessage()
            )));
        }
    }

    @DeleteMapping
    public ResponseEntity<HashMap<String , String>> deleteUser(@RequestBody Users users){
        try{
            usersService.deleteUserById(users);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status", "1",
                    "message","User has deleted Successfully"
            )));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status", "0",
                    "message",e.getMessage()
            )));
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<List<UserResponseDTO>> getUsersForAdmin(){
        return ResponseEntity.ok(usersService.getUsersForAdmin());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Users> updateStatus(@PathVariable Long id , @RequestParam String status){
        return ResponseEntity.ok(usersService.updateUserStatus(id, status));
    }

    @GetMapping("/admin/stats")
    public AdminStatsDTO getStatus(){
        return usersService.getStats();
    }
}
