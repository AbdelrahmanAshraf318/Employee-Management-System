package com.example.Employee.Management.System.repository;

import com.example.Employee.Management.System.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID>
{
    User findByEmail(String email);
}
