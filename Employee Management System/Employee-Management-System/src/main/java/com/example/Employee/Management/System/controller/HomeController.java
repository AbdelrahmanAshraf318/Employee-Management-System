package com.example.Employee.Management.System.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController
{
    @GetMapping("/default")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Admin Dashboard: Access granted!");
    }
}
