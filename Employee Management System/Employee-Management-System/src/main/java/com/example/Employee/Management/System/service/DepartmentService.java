package com.example.Employee.Management.System.service;

import com.example.Employee.Management.System.models.Department;
import com.example.Employee.Management.System.repository.DepartmentRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService
{
    private final DepartmentRepo departmentRepo;

    public DepartmentService(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    public Department createDepartment(Department department) {
        if (departmentRepo.existsByNameAndCompanyId(department.getDept_name(), department.getCompany().getId())) {
            throw new IllegalArgumentException("Department name must be unique within the same company");
        }
        return departmentRepo.save(department);
    }

    

}
