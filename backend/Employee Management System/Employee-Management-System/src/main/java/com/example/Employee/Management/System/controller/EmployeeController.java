package com.example.Employee.Management.System.controller;


import com.example.Employee.Management.System.dtos.EmployeeDTO;
import com.example.Employee.Management.System.models.Employee;
import com.example.Employee.Management.System.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class EmployeeController
{
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService)
    {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public ResponseEntity<String> greetEmployee(Authentication authentication)
    {
        String username = authentication.getName();
        return ResponseEntity.ok("Welcome " + username + "!");
    }

    // Add an employee
    @PostMapping("/{company_name}/employees")
    public ResponseEntity<Employee> addEmployeeToCompany(@RequestBody Employee employee,
                                                         @PathVariable String company_name)
    {
        try
        {
            Employee saved_employee = employeeService.insertEmployee(employee, company_name).getBody();
            return ResponseEntity.status(HttpStatus.CREATED).body(saved_employee);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{comp_id}/employees/{emp_id}")
    public ResponseEntity<?> updateEmployee(@PathVariable UUID comp_id,
                                                   @PathVariable UUID emp_id,
                                                   @RequestBody Employee employee)
    {
        try {
            Employee updated = employeeService.updateEmployee(employee,
                    emp_id, comp_id);
            return ResponseEntity.ok(updated);
        }catch (EntityNotFoundException | IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{comp_id}/employees/{emp_id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID comp_id,
                                               @PathVariable UUID emp_id)
    {
        try {
            employeeService.deleteEmployee(emp_id, comp_id);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        }catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found
        }
    }

    @GetMapping("/{comp_id}/employees")
    public ResponseEntity<List<Employee>> getAllEmployee(@PathVariable UUID comp_id)
    {
        try {
            List<Employee> employees = employeeService.getAllEmployee(comp_id);
            return ResponseEntity.ok(employees);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{comp_id}/employees/{emp_id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable UUID emp_id,
                                                    @PathVariable UUID comp_id)
    {
        try {
            Employee employee = employeeService.getEmployeeById(emp_id, comp_id);
            return ResponseEntity.ok(employee);
        }catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found
        }
    }

}
