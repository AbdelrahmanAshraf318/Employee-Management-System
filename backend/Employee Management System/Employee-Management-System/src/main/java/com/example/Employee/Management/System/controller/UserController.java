package com.example.Employee.Management.System.controller;


import com.example.Employee.Management.System.dtos.RegisterRequest;
import com.example.Employee.Management.System.models.User;
import com.example.Employee.Management.System.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController
{
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request)
    {
        String token = userService.registerUser(request);
        logger.info("JWT token generated successfully for user: {}", request.getUsername());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }


}
