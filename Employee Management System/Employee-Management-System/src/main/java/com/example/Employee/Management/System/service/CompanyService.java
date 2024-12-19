package com.example.Employee.Management.System.service;


import com.example.Employee.Management.System.models.Company;
import com.example.Employee.Management.System.repository.CompanyRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyService
{
    private final CompanyRepo companyRepo;

    public CompanyService(CompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }

    public Company createCompany(Company company)
    {
        if(companyRepo.existsByName(company.getCompany_name()))
        {
            throw new IllegalArgumentException("Company name must be unique");
        }
        return companyRepo.save(company);
    }

    public List<Company> getAllCompanies()
    {
        return companyRepo.findAll();
    }

    public Company getCompanyById(UUID id) {
        return companyRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Company not found"));
    }

    public Company updateCompany(UUID id, Company updatedCompany) {
        Company company = getCompanyById(id);
        if (!company.getCompany_name().equals(updatedCompany.getCompany_name()) &&
                companyRepo.existsByName(updatedCompany.getCompany_name())) {
            throw new IllegalArgumentException("Company name must be unique");
        }
        company.setCompany_name(updatedCompany.getCompany_name());
        return companyRepo.save(company);
    }

    public void deleteCompany(UUID id) {
        if (!companyRepo.existsById(id)) {
            throw new IllegalArgumentException("Company not found");
        }
        companyRepo.deleteById(id);
    }
}
