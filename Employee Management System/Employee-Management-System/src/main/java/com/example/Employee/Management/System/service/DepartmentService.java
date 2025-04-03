package com.example.Employee.Management.System.service;

import com.example.Employee.Management.System.dtos.DepartmentDTO;
import com.example.Employee.Management.System.exception.ResourceNotFoundException;
import com.example.Employee.Management.System.models.Company;
import com.example.Employee.Management.System.models.Department;
import com.example.Employee.Management.System.repository.CompanyRepo;
import com.example.Employee.Management.System.repository.DepartmentRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService
{
    private final DepartmentRepo departmentRepo;
    private final CompanyRepo companyRepo;

    public DepartmentService(DepartmentRepo departmentRepo, CompanyRepo companyRepo)
    {
        this.departmentRepo = departmentRepo;
        this.companyRepo = companyRepo;
    }

    //Get All Departments in a specific company
    public List<Department> getDepartmentsByCompany(UUID company_id)
    {
        return departmentRepo.findByCompanyId(company_id);
    }

    // Delete a specific Department
    public void deleteDepartmentByCompany(UUID dept_id, UUID comp_id)
    {
        if(!companyRepo.existsById(comp_id))
        {
            throw new ResourceNotFoundException("Company not found");
        }
        // Check if the department exists for the given company
        if (!departmentRepo.existsByIdAndCompanyId(dept_id, comp_id)) {
            throw new ResourceNotFoundException("Department not found in the specified company");
        }
        departmentRepo.deleteByIdAndCompanyId(dept_id, comp_id);
    }

    // Insert a department into a specific company
    public Department createDepartmentByCompany(Department department, UUID comp_id)
    {
        Company company = companyRepo.findById(comp_id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        if(departmentRepo.existsByIdAndCompanyId(department.getId(), comp_id))
        {
            throw new IllegalArgumentException("Department already exists in the specified company");
        }
        department.setCompany(company);

        return departmentRepo.save(department);
    }

    // Update a department into a specific company
    public DepartmentDTO UpdateDepartmentByCompany(DepartmentDTO departmentDTO, UUID comp_id, UUID dept_id)
    {
        // Retrieve the company
        Company company = companyRepo.findById(comp_id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        // Retrieve the department
        Department department = departmentRepo.findById(dept_id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        // Ensure that the department is inside the specific company
        if (!department.getCompany().getId().equals(comp_id)) {
            throw new IllegalArgumentException("The department does not belong to the specified company");
        }



        // Update modifiable fields
        department.setDept_name(departmentDTO.getDept_name());
        department.setCompany(departmentDTO.getCompany());
        department.setEmployees(departmentDTO.getEmployees());
        department.setNumberOfEmployees(departmentDTO.getNumberOfEmployees());

        Department updatedDept = departmentRepo.save(department);

        // Return the updated department
        return new DepartmentDTO(
                updatedDept.getCompany(),
                updatedDept.getDept_name(),
                updatedDept.getNumberOfEmployees(),
                updatedDept.getEmployees()
        );
    }


}
