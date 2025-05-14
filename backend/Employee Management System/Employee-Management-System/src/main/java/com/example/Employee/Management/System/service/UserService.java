package com.example.Employee.Management.System.service;


import com.example.Employee.Management.System.models.User;
import com.example.Employee.Management.System.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService
{
    private final UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserService(UserRepo userRepo)
    {
        this.userRepo = userRepo;
    }



    public User registerUser(User user)
    {
        // Encode the raw password
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepo.save(user);
    }

    @Cacheable(value = "users", key = "#id")
    public User findById(UUID id)
    {
        System.out.println("Get from DB");
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }


    @CacheEvict(cacheNames = "users", key = "#user.id")
    public ResponseEntity<?> updateUser(UUID id, Map<String, Object> updates)
    {
        Optional<User> existingUser = userRepo.findById(id);
        if(existingUser.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User user = existingUser.get();
        updates.forEach(
                (key, value) -> {
                    Field field = ReflectionUtils.findField(User.class, key);
                    if (field != null)
                    {
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, user, value);
                    }
                }
        );

        return ResponseEntity.ok(userRepo.save(user)); // ✅ Safe update
    }

    public List<User> getAllUsers()
    {
        return userRepo.findAll();
    }

    public Optional<User> findByUsername(String username)
    {
        return userRepo.findByUsername(username);
    }



}
