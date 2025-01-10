package com.example.Employee.Management.System.service;

import com.example.Employee.Management.System.exception.ResourceNotFoundException;
import com.example.Employee.Management.System.models.Company;
import com.example.Employee.Management.System.repository.CompanyRepo;
import org.springframework.stereotype.Service;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyService
{
    private final CompanyRepo companyRepo;

    public CompanyService(CompanyRepo companyRepo)
    {
        this.companyRepo = companyRepo;
    }

    // Get All Companies
    public List<Company> getAllCompanies()
    {
        return companyRepo.findAll();
    }

    // Delete a specific company
    public void deleteCompany(UUID company_id)
    {
        Company find_company = companyRepo.findById(company_id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        companyRepo.deleteById(company_id);
    }

    // Update a specific company
    public Company updateCompany(UUID company_id, Company update_company)
    {
        Company find_company = companyRepo.findById(company_id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        find_company.setCompany_name(update_company.getCompany_name());
        find_company.setDepartments(update_company.getDepartments());

        return companyRepo.save(find_company);
    }

    // Get a specific company
    public Company getCompany(UUID company_id)
    {
        Company company = companyRepo.findById(company_id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        return company;
    }
}
