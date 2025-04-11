package com.example.Employee.Management.System.controller;


import com.example.Employee.Management.System.dtos.CompanyDTO;
import com.example.Employee.Management.System.models.Company;
import com.example.Employee.Management.System.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody @Valid Company company)
    {
        Company createdCompany = companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
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
    @PatchMapping("/{id}")
    public CompanyDTO updateCompany(UUID id, @RequestBody CompanyDTO companyDTO)
    {
        return companyService.updateCompany(id, companyDTO);
    }

    // Delete a company
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable UUID id)
    {
        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }
}
