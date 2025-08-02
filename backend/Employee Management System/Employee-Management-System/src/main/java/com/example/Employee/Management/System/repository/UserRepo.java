package com.example.Employee.Management.System.repository;

import com.example.Employee.Management.System.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin("http://localhost:4200")
@Repository
public interface UserRepo extends JpaRepository<User, UUID>
{
    Optional<User> findByUsername( String username);
}
