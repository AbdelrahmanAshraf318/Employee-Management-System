package com.example.Employee.Management.System.service;

import com.example.Employee.Management.System.models.Company;
import com.example.Employee.Management.System.models.Department;
import com.example.Employee.Management.System.models.Employee;
import com.example.Employee.Management.System.repository.CompanyRepo;
import com.example.Employee.Management.System.repository.DepartmentRepo;
import com.example.Employee.Management.System.repository.EmployeeRepo;
import jakarta.persistence.EntityNotFoundException;
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
    public Employee updateEmployee(Employee update_employee, UUID emp_id, UUID comp_id)
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
        if(update_employee != null)
        {
            Department department = departmentRepo.findById(update_employee.getDepartment().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " +
                            update_employee.getDepartment().getId()));

            if(!department.getCompany().getId().equals(comp_id))
            {
                throw new IllegalArgumentException("The new department does not belong to the specified company");
            }
            exist_employee.setDepartment(department);
        }
        exist_employee.setName(update_employee.getName());
        exist_employee.setAddress(update_employee.getAddress());
        exist_employee.setDesignation(update_employee.getDesignation());
        exist_employee.setPhoneNumber(update_employee.getPhoneNumber());
        exist_employee.setStatus(update_employee.getStatus());
        exist_employee.setHourly_rate(update_employee.getHourly_rate());
        exist_employee.setWorkingHours(update_employee.getWorkingHours());

        return employeeRepo.save(exist_employee);
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
