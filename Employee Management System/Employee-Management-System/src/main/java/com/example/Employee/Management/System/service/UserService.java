package com.example.Employee.Management.System.service;


import com.example.Employee.Management.System.models.User;
import com.example.Employee.Management.System.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService
{
    private final UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo)
    {
        this.userRepo = userRepo;
    }



    public void registerUser(User user)
    {
        // Encode the raw password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
    }

    public User findById(UUID id)
    {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }


    public List<User> getAllUsers()
    {
        return userRepo.findAll();
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
