package com.example.Employee.Management.System.repository;

import com.example.Employee.Management.System.models.Company;
import com.example.Employee.Management.System.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepo extends JpaRepository<Department, UUID>
{
    List<Department> findByCompanyId(UUID id);

    Optional<Department> findByIdAndCompanyId(UUID departmentId, UUID companyId);
    boolean existsByIdAndCompanyId(UUID departmentId, UUID companyId);
    void deleteByIdAndCompanyId(UUID departmentId, UUID companyId);



}
