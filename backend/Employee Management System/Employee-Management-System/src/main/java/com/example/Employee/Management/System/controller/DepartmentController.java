package com.example.Employee.Management.System.controller;


import com.example.Employee.Management.System.dtos.DepartmentDTO;
import com.example.Employee.Management.System.models.Department;
import com.example.Employee.Management.System.service.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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


    @GetMapping("/{comp_id}")
    public ResponseEntity<List<Department>> getDepartmentsByCompany(@PathVariable UUID comp_id)
    {
        List<Department> departments = departmentService.getDepartmentsByCompany(comp_id);
        return ResponseEntity.ok(departments);
    }

    @DeleteMapping("/{comp_id}/{dept_id}")
    public ResponseEntity<Void> deleteDepartmentByCompany(@PathVariable UUID comp_id,
                                                          @PathVariable UUID dept_id)
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

    @PutMapping("/{comp_id}/{dept_id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable UUID comp_id,
                                @PathVariable UUID dept_id,
                               @RequestBody DepartmentDTO departmentDTO)
    {
        try {
            DepartmentDTO department = departmentService.UpdateDepartmentByCompany(departmentDTO,
                    comp_id, dept_id);
            return ResponseEntity.ok(department);
        }catch (EntityNotFoundException xe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (IllegalArgumentException xe){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
}
