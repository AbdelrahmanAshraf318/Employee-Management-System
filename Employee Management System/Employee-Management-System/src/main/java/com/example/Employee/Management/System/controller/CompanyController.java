package com.example.Employee.Management.System.controller;


import com.example.Employee.Management.System.models.Company;
import com.example.Employee.Management.System.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/companies")
public class CompanyController
{
    private final CompanyService companyService;

    // Using Constructor Injection
    public CompanyController(CompanyService companyService)
    {
        this.companyService = companyService;
    }

    // Get all companies
    @GetMapping
    public List<Company> getAllCompanies()
    {
        return companyService.getAllCompanies();
    }

    // Get a specific company
    @GetMapping("/{id}")
    public Company getCompany(@PathVariable UUID id)
    {
        return companyService.getCompany(id);
    }

    // Update a company
    @PutMapping("/{id}")
    public Company updateCompany(UUID id, @RequestBody Company company)
    {
        return companyService.updateCompany(id, company);
    }

    // Delete a company
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable UUID id)
    {
        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }
}
