package com.example.Employee.Management.System.repository;

import com.example.Employee.Management.System.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepo extends JpaRepository<Employee, UUID>
{
    // Custom query to find all employees by company ID
    List<Employee> findByCompanyId(UUID companyId);
}
