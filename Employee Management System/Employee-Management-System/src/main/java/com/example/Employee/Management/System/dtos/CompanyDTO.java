package com.example.Employee.Management.System.dtos;

import com.example.Employee.Management.System.models.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CompanyDTO
{
    private String companyName;
    private int numberOfDepartments;
    private int numberOfEmployees;
    private List<Department> departments;
}
