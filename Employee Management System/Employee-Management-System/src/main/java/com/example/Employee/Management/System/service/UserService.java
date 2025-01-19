package com.example.Employee.Management.System.service;


import com.example.Employee.Management.System.models.User;
import com.example.Employee.Management.System.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService
{
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo)
    {
        this.userRepo = userRepo;
    }

    public User findById(UUID id)
    {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }


    public Optional<User> findByUsername(String username)
    {
        return userRepo.findByUsername(username);
    }

    public User saveUser(User user)
    {
        return userRepo.save(user);
    }
}
