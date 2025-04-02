package com.example.Employee.Management.System.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company; // Reference to Company entity


}
