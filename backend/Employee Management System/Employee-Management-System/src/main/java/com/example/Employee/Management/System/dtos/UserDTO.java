package com.example.Employee.Management.System.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDTO
{
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String role;
}
