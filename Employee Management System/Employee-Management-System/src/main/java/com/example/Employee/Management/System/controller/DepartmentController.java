package com.example.Employee.Management.System.controller;


import com.example.Employee.Management.System.models.Department;
import com.example.Employee.Management.System.service.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController
{
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService)
    {
        this.departmentService = departmentService;
    }

    @GetMapping("/company/{comp_id}")
    public ResponseEntity<List<Department>> getDepartmentsByCompany(@PathVariable UUID comp_id)
    {
        List<Department> departments = departmentService.getDepartmentsByCompany(comp_id);
        return ResponseEntity.ok(departments);
    }

    @DeleteMapping("/company/{comp_id}/department/{dept_id}")
    public ResponseEntity<Void> deleteDepartmentByCompany(@PathVariable UUID dept_id
            ,@PathVariable UUID comp_id)
    {
        try {
            departmentService.deleteDepartmentByCompany(dept_id, comp_id);
            return ResponseEntity.noContent().build();  // Return 204 No Content if deletion is successful
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return 404 Not Found if the department or company is not found
        }
    }

    @PostMapping("/company/{comp_id}")
    public ResponseEntity<Department> createDepartment(@PathVariable UUID comp_id,
                                                       @RequestBody Department department)
    {
        try
        {
            Department createdDepartment = departmentService.createDepartmentByCompany(department, comp_id);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Company not found
        }catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Duplicate department
        }
    }

    @PutMapping("/company/{comp_id}/department/{dept_id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable UUID comp_id,
                                @PathVariable UUID dept_id,
                               @RequestBody Department updated_department)
    {
        try {
            Department department = departmentService.UpdateDepartmentByCompany(updated_department,
                    comp_id, dept_id);
            return ResponseEntity.ok(department);
        }catch (EntityNotFoundException xe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (IllegalArgumentException xe){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
}
