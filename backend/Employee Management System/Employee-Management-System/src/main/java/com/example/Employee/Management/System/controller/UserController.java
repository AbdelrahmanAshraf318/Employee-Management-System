package com.example.Employee.Management.System.controller;


import com.example.Employee.Management.System.dtos.EmployeeDTO;
import com.example.Employee.Management.System.dtos.UserDTO;
import com.example.Employee.Management.System.models.User;
import com.example.Employee.Management.System.service.UserService;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, Map<String, Object> updates)
    {
        return userService.updateUser(id, updates);
    }

    @GetMapping
    public List<User> getUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping("/{user_id}")
    public User getUserById(@PathVariable UUID user_id)
    {
        return userService.findById(user_id);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User saveUser = userService.registerUser(user);
        return ResponseEntity.ok(saveUser);
    }

    }
