package com.example.Employee.Management.System.repository;

import com.example.Employee.Management.System.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID>
{
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department.company.id = :companyId")
    UUID countByCompanyId(@Param("companyId") UUID companyId);
    User findByEmail(String email);
}
