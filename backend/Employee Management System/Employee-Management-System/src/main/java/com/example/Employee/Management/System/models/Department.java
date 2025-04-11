package com.example.Employee.Management.System.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "DEPARTMENT",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"dept_name", "company_id"} // Enforce unique department names within a company
        )
)
public class Department
{
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "department_id", columnDefinition = "RAW(16)", unique = true, nullable = false)
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company; // Reference to Company entity

    @NotBlank(message = "Department name is mandatory")
    @Column(name = "dept_name", unique = true, nullable = false) // Enforce uniqueness
    private String dept_name;

    @Column(name = "numberOfEmployees", nullable = false)
    private int numberOfEmployees;


    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true) // One-to-Many relationship
    private List<Employee> employees;


}
