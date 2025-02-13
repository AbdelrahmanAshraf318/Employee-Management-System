package com.example.Employee.Management.System.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController
{
    @GetMapping("/")
    public String greet()
    {
        return "Welcome to Employee Management System";
    }
}
