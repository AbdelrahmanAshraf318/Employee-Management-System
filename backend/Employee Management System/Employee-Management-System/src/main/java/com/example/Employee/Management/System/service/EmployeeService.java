package com.example.Employee.Management.System.service;

import com.example.Employee.Management.System.dtos.EmployeeDTO;
import com.example.Employee.Management.System.models.Company;
import com.example.Employee.Management.System.models.Department;
import com.example.Employee.Management.System.models.Employee;
import com.example.Employee.Management.System.repository.CompanyRepo;
import com.example.Employee.Management.System.repository.DepartmentRepo;
import com.example.Employee.Management.System.repository.EmployeeRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService
{
    private final EmployeeRepo employeeRepo;
    private final DepartmentRepo departmentRepo;
    private final CompanyRepo companyRepo;

    public EmployeeService(EmployeeRepo employeeRepo, DepartmentRepo departmentRepo, CompanyRepo companyRepo)
    {
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
        this.companyRepo = companyRepo;
    }

    // Get All Employee
    public List<Employee> getAllEmployee(UUID comp_id)
    {
        companyRepo.findById(comp_id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        return employeeRepo.findByCompanyId(comp_id);
    }

    // Get Employee by ID
    @Cacheable(value = "employee", key = "#id")
    public Employee getEmployeeById(UUID emp_id, UUID comp_id)
    {
        Company company = companyRepo.findById(comp_id)
                .orElseThrow(() -> new EntityNotFoundException("Company Not Found"));

        Employee employee = employeeRepo.findById(emp_id)
                .orElseThrow(() -> new EntityNotFoundException("Employee Not Found"));

        if(!employee.getCompany().getId().equals(comp_id))
        {
            throw new IllegalArgumentException("Employee does not belong to the specified company");
        }
        return employee;
    }

    // Add an Employee
    public Employee insertEmployee(Employee employee, UUID comp_id)
    {
        Company company = companyRepo.findById(comp_id)
                .orElseThrow(() -> new EntityNotFoundException("Company Not Found"));

        employee.setCompany(company);

        return employeeRepo.save(employee);

    }

    // Update an Employee
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, UUID emp_id, UUID comp_id)
    {
        Company company = companyRepo.findById(comp_id)
                .orElseThrow(() -> new EntityNotFoundException("Company Not Found"));

        Employee exist_employee = employeeRepo.findById(emp_id)
                .orElseThrow(() -> new EntityNotFoundException("Employee Not Found"));

        // Ensure that employee's department belongs to the same company
        if(!exist_employee.getDepartment().getCompany().getId().equals(comp_id))
        {
            throw new IllegalArgumentException("Employee's department does not belong to the same company the employee belongs to");
        }

        // Ensure that the updated department in the updated_employee belongs to the company
        if(employeeDTO != null)
        {
            Department department = departmentRepo.findById(exist_employee.getDepartment().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " +
                            exist_employee.getDepartment().getId()));

            if(!department.getCompany().getId().equals(comp_id))
            {
                throw new IllegalArgumentException("The new department does not belong to the specified company");
            }
        }
        exist_employee.setName(employeeDTO.getName());
        exist_employee.setAddress(employeeDTO.getAddress());
        exist_employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        exist_employee.setEmail(employeeDTO.getEmail());
        exist_employee.setPassword(employeeDTO.getPassword());
        exist_employee.setUsername(employeeDTO.getUsername());

        Employee employee = employeeRepo.save(exist_employee);

        return new EmployeeDTO(
                employee.getName(),
                employeeDTO.getEmail(),
                employeeDTO.getAddress(),
                employeeDTO.getPhoneNumber(),
                employeeDTO.getUsername(),
                employeeDTO.getPassword()
        );
    }

    // Delete an Employee
    public void deleteEmployee(UUID emp_id, UUID comp_id)
    {
        Company company = companyRepo.findById(comp_id)
                .orElseThrow(() -> new EntityNotFoundException("Company Not Found"));

        Employee employee = employeeRepo.findById(emp_id)
                .orElseThrow(() -> new EntityNotFoundException("Employee Not Found"));

        if(!employee.getCompany().getId().equals(comp_id))
        {
            throw new IllegalArgumentException("Employee does not belong to the specified company");
        }

        employeeRepo.deleteById(emp_id);

    }
}
