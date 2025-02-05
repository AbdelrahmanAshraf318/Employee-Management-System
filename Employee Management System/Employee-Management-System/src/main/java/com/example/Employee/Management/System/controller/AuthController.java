package com.example.Employee.Management.System.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController
{
    @GetMapping("/")
    public String home() {
        return "index"; // Renders the index.html template
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // This should be your login page
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup"; // This should be your signup page
    }
}
