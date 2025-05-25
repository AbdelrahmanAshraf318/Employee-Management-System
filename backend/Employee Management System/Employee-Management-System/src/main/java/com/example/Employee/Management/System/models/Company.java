package com.example.Employee.Management.System.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity(name = "COMPANY")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COMPANY")
public class Company
{
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "company_id", columnDefinition = "RAW(16)", unique = true, nullable = false)
    private UUID id;

    @NotBlank(message = "Company name is mandatory")
    @Column(name = "company_name", unique = true, nullable = false) // Enforce uniqueness
    private String company_name;

    @Column(name = "numberOfDepartments", nullable = false, columnDefinition = "int default 0")
    private int numberOfDepartments;

    @Column(name = "numberOfEmployees", nullable = true, columnDefinition = "int default 0")
    private int numberOfEmployees;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true) // One-to-Many relationship
    private List<Department> departments;


}
