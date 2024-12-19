package com.example.Employee.Management.System.service;


import com.example.Employee.Management.System.models.Company;
import com.example.Employee.Management.System.repository.CompanyRepo;
import com.example.Employee.Management.System.repository.DepartmentRepo;
import com.example.Employee.Management.System.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyService
{
    private final CompanyRepo companyRepo;
    private final DepartmentRepo departmentRepo;
    private final UserRepo userRepo;

    public CompanyService(CompanyRepo companyRepo, DepartmentRepo departmentRepo, UserRepo userRepo) {
        this.companyRepo = companyRepo;
        this.departmentRepo = departmentRepo;
        this.userRepo = userRepo;
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
        List<Company> companies = companyRepo.findAll();
        companies.forEach(this::calculateDynamicFields);
        return companies;
    }

    public Company getCompanyById(UUID id)
    {
        Company company = companyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
        calculateDynamicFields(company);
        return company;
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
    private void calculateDynamicFields(Company company) {
        long departmentCount = departmentRepo.countByCompanyId(company.getId());
        long employeeCount = userRepo.countByCompanyId(company.getId());
        company.setNumberOfDepartments((int) departmentCount);
        company.setNumberOfEmployees((int) employeeCount);
    }
}
