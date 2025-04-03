package com.example.Employee.Management.System.service;

import com.example.Employee.Management.System.dtos.CompanyDTO;
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

    public Company createCompany(Company company)
    {
        if(companyRepo.existsById(company.getId()))
            throw new IllegalArgumentException("Company you need to add is already exist");
        return companyRepo.save(company);
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
    public CompanyDTO updateCompany(UUID company_id, CompanyDTO companyDTO)
    {
        Company find_company = companyRepo.findById(company_id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        find_company.setCompany_name(companyDTO.getCompanyName());
        find_company.setNumberOfDepartments(companyDTO.getNumberOfDepartments());
        find_company.setNumberOfEmployees(companyDTO.getNumberOfEmployees());
        find_company.setDepartments(companyDTO.getDepartments());

        Company updatedCompany = companyRepo.save(find_company);

        return new CompanyDTO(updatedCompany.getCompany_name(),
                updatedCompany.getNumberOfDepartments(),
                updatedCompany.getNumberOfEmployees(),
                updatedCompany.getDepartments());
    }

 ;   // Get a specific company
    public Company getCompany(UUID company_id)
    {
        Company company = companyRepo.findById(company_id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        return company;
    }
}
