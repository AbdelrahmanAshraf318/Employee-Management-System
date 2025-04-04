package com.example.Employee.Management.System.controller;


import com.example.Employee.Management.System.models.User;
import com.example.Employee.Management.System.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
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

    @PostMapping
    public User createUser(@RequestBody User user)
    {
        return userService.saveUser(user);
    }
}
