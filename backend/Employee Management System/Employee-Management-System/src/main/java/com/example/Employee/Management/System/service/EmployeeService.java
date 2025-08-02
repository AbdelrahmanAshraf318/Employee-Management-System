package com.example.Employee.Management.System.service;

import com.example.Employee.Management.System.models.Company;
import com.example.Employee.Management.System.models.Department;
import com.example.Employee.Management.System.models.Employee;
import com.example.Employee.Management.System.models.User;
import com.example.Employee.Management.System.repository.CompanyRepo;
import com.example.Employee.Management.System.repository.DepartmentRepo;
import com.example.Employee.Management.System.repository.EmployeeRepo;
import com.example.Employee.Management.System.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService
{
    private final EmployeeRepo employeeRepo;
    private final DepartmentRepo departmentRepo;
    private final CompanyRepo companyRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public EmployeeService(EmployeeRepo employeeRepo, DepartmentRepo departmentRepo,
                           CompanyRepo companyRepo, PasswordEncoder passwordEncoder, UserRepo userRepo)
    {
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
        this.companyRepo = companyRepo;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
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
    @Transactional
    public ResponseEntity<Employee> insertEmployee(Employee employee, String company_name)
    {
        Company company = companyRepo.existsByCompanyName(company_name)
                .orElseThrow(() -> new EntityNotFoundException("Company Not Found"));

        employee.setCompany(company);

        User user = userRepo.findById(employee.getId())
                .orElseThrow( () -> new EntityNotFoundException("User not found") );

        Department dept = departmentRepo.findByDepartmentName(employee.getDepartment().getDept_name())
                .orElseThrow( () -> new EntityNotFoundException("Department Not Found"));

        // Verify that the department belongs to the same company
        if (!dept.getCompany().getCompany_name().equals(company_name))
        {
            throw new IllegalArgumentException("Department belongs to different company");
        }

        Employee newEmployee = new Employee();
        newEmployee.setDesignation(employee.getDesignation());
        newEmployee.setHiredOn(employee.getHiredOn());
        newEmployee.setDepartment(dept);
        newEmployee.setAddress(employee.getAddress());
        newEmployee.setPhoneNumber(employee.getPhoneNumber());
        newEmployee.setCompany(company);

        return ResponseEntity.ok(employeeRepo.save(employee));

    }

    // Update an Employee
    @Transactional
    public Employee updateEmployee(Employee employee, UUID emp_id, UUID comp_id)
    {
        // 1. Validate input
        if (employee == null) {
            throw new IllegalArgumentException("Employee update data cannot be null");
        }

        // 2. Check ID consistency
        if (employee.getId() != null && !employee.getId().equals(emp_id))
        {
            throw new IllegalArgumentException("Employee ID in body doesn't match path ID");
        }

        // 3. Verify company exists
        companyRepo.findById(comp_id)
                .orElseThrow(() -> new EntityNotFoundException("Company Not Found"));

        // 4. Fetch existing employee
        Employee existing = employeeRepo.findById(emp_id)
                .orElseThrow(() -> new EntityNotFoundException("Employee Not Found"));

        // 5. Validate and update department (if provided)
        if (employee.getDepartment() != null)
        {
            Department newDept = departmentRepo.findById(employee.getDepartment().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found"));

            if (!newDept.getCompany().getId().equals(comp_id))
            {
                throw new IllegalArgumentException("Department belongs to different company");
            }
            existing.setDepartment(newDept);
        }

        // 6. Patch allowed fields (only update non-null values)
        if (employee.getAddress() != null)
        {
            existing.setAddress(employee.getAddress());
        }
        if (employee.getPhoneNumber() != null)
        {
            existing.setPhoneNumber(employee.getPhoneNumber());
        }
        if (employee.getEmail() != null)
        {
            existing.setEmail(employee.getEmail());
        }

        // 7. Special handling for password (hash if provided)
        if (employee.getPassword() != null)
        {
            existing.setPassword(passwordEncoder.encode(employee.getPassword()));
        }

        // 8. Block updates to immutable fields
        if (employee.getHiredOn() != null)
        {
            throw new IllegalArgumentException("hiredOn field is immutable");
        }
        if (employee.getUsername() != null)
        {
            throw new IllegalArgumentException("username field is immutable");
        }

        return employeeRepo.save(existing);
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
