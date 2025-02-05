package com.example.Employee.Management.System.service;


import com.example.Employee.Management.System.models.User;
import com.example.Employee.Management.System.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
