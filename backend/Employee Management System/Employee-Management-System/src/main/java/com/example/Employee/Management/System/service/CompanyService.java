package com.example.Employee.Management.System.service;

import com.example.Employee.Management.System.dtos.CompanyDTO;
import com.example.Employee.Management.System.exception.ResourceNotFoundException;
import com.example.Employee.Management.System.models.Company;
import com.example.Employee.Management.System.repository.CompanyRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.Cacheable;
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
        if(!companyRepo.existsByCompanyName(company.getCompany_name()).isEmpty())
            throw new IllegalArgumentException("Company with name = "
            + company.getCompany_name() + " is already exist");
        return companyRepo.save(company);
    }

    public UUID getCompanyIdByName(String company_name)
    {
        return companyRepo.findCompanyIdByName(company_name)
                .orElseThrow(
                        () -> new EntityNotFoundException("No Company found with name = " + company_name)
                );
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

    // Get a specific company
    @Cacheable(value = "company", key = "#id")
    public Company getCompany(UUID company_id)
    {
        Company company = companyRepo.findById(company_id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        return company;
    }
}
