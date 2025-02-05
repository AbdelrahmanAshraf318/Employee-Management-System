package com.example.Employee.Management.System.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController
{
    @GetMapping("/login")
    public String login() {
        return "login"; // This will look for login.html in src/main/resources/templates
    }
}
