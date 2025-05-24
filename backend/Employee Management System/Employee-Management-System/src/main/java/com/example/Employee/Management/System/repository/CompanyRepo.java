package com.example.Employee.Management.System.repository;

import com.example.Employee.Management.System.models.Company;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin("http://localhost:4200")
@Repository
public interface CompanyRepo extends JpaRepository<Company, UUID>
{
    @Query("SELECT c.id FROM COMPANY c WHERE c.company_name = :company_name")
    Optional<UUID> findCompanyIdByName(@Param("company_name") String company_name);

    @Query("SELECT c.company_name FROM COMPANY c WHERE c.company_name = :company_name")
    Optional<Company> existsByCompanyName(@Param("company_name") String company_name);
}
