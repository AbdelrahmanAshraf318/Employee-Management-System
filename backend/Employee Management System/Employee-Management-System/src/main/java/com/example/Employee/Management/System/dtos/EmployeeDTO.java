package com.example.Employee.Management.System.dtos;

import com.example.Employee.Management.System.models.Company;
import com.example.Employee.Management.System.models.Department;
import com.example.Employee.Management.System.models.EmployeeStatus;
import com.example.Employee.Management.System.models.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeDTO
{
     private String  title;

     private LocalDate hiredDate;

     private String address;

    private String phoneNumber;

}
