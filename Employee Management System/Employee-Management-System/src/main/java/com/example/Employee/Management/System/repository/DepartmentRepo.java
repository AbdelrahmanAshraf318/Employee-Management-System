package com.example.Employee.Management.System.repository;

import com.example.Employee.Management.System.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface DepartmentRepo extends JpaRepository<Department, UUID>
{
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department.id = :departmentId")
    UUID countEmployeesByDepartmentId(@Param("departmentId") UUID departmentId);
    boolean existsByNameAndCompanyId(String name, UUID companyId); // Ensure name uniqueness within a company
}
