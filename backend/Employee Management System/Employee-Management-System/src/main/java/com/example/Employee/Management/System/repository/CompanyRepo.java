package com.example.Employee.Management.System.repository;

import com.example.Employee.Management.System.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.UUID;

@CrossOrigin("http://localhost:4200")
@Repository
public interface CompanyRepo extends JpaRepository<Company, UUID>
{

}
