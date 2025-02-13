package com.example.Employee.Management.System.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "EMPLOYEE")
public class Employee extends User
{
    @NotBlank(message = "Name is mandatory")
    @Size(min = 4, message = "Name must be at least 4 characters long")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Email is mandatory")  // Validates non-null and non-empty
    @Email(message = "Email should be valid")  // Validates email format
    @Column(name = "email", unique = true, nullable = false)   // Ensures uniqueness and non-null in DB
    private String email;

    @Column(name = "designation", nullable = false)
    private String designation; // Position or title

    @Column(name = "hired_On")
    private LocalDate hiredOn;

    @Column(name = "days_Employed")
    private int daysEmployed;


    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false) // Many employees belong to one department
    private Department department;

    @Column(name = "address")
    private String  address;

    @Column(name = "working_hours", nullable = false)
    private double workingHours;

    @Column(name = "hourly_rate", nullable = false)
    private double hourly_rate;

    @Column(name = "salary")
    private double salary;


    @Pattern(regexp = "^[0-9]{11}$", message = "Phone number must be 11 digits")
    @Column(name = "phone_Number")
    private String phoneNumber;


    //@NotNull(message = "Status is mandatory")
    @Enumerated(EnumType.STRING) // Store the enum value as a string in the database
    @Column(name = "status", nullable = false)
    private EmployeeStatus status;


    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
    }

    public double getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(double hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public double getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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



    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
