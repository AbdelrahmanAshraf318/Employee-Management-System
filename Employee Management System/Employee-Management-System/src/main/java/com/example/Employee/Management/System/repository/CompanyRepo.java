package com.example.Employee.Management.System.repository;

import com.example.Employee.Management.System.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepo extends JpaRepository<Company, UUID>
{

}
