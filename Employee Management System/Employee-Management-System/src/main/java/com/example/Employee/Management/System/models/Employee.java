package com.example.Employee.Management.System.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Entity
public class Employee extends User
{
    @Column(nullable = false)
    private String designation; // Position or title

    @Column
    private LocalDate hiredOn;

    @Column
    private int daysEmployed;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company; // Reference to Company entity

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false) // Many employees belong to one department
    private Department department;

    private String  address;

    @Pattern(regexp = "^[0-9]{11}$", message = "Phone number must be 11 digits")
    private String phoneNumber;


    //@NotNull(message = "Status is mandatory")
    @Enumerated(EnumType.STRING) // Store the enum value as a string in the database
    @Column(nullable = false)
    private EmployeeStatus status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDate getHiredOn() {
        return hiredOn;
    }

    public void setHiredOn(LocalDate hiredOn) {
        this.hiredOn = hiredOn;
    }

    public int getDaysEmployed() {
        return daysEmployed;
    }

    public void setDaysEmployed(int daysEmployed) {
        this.daysEmployed = daysEmployed;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
