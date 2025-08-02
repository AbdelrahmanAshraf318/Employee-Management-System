package com.example.Employee.Management.System.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "USER_TABLE")
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "user_id", columnDefinition = "RAW(16)", unique = true, nullable = false)
    private UUID id;

    @NotBlank(message = "Email is mandatory")  // Validates non-null and non-empty
    @Email(message = "Email should be valid")  // Validates email format
    @Column(name = "email", unique = true, nullable = false)   // Ensures uniqueness and non-null in DB
    private String email;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 4, message = "Name must be at least 4 characters long")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 6, message = "Username must be at least 6 characters long")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "password")
    private String password;

    @NotNull(message = "Role is mandatory")
    @Enumerated(EnumType.STRING) // Store the enum value as a string in the database
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employee employee;

}