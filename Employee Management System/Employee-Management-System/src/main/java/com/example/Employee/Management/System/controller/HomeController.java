package com.example.Employee.Management.System.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController
{
    @GetMapping({"/", "/index", "/index.html"})
    public String home() {
        return "index"; // Returns index.html from templates
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup"; // Returns signup.html from templates
    }
}
