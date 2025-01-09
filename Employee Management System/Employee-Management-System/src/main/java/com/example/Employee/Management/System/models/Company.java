package com.example.Employee.Management.System.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public class Company
{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID id;

    @NotBlank(message = "Company name is mandatory")
    @Column(unique = true, nullable = false) // Enforce uniqueness
    private String company_name;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int numberOfDepartments;

    @Column(nullable = true, columnDefinition = "int default 0")
    private int numberOfEmployees;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true) // One-to-Many relationship
    private List<Department> departments;

    public UUID getId() {
        return id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public int getNumberOfDepartments() {
        return numberOfDepartments;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setNumberOfDepartments(int numberOfDepartments) {
        this.numberOfDepartments = numberOfDepartments;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Department> getDepartments() {
        return departments;
    }
}
