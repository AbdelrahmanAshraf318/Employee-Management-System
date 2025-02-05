package com.example.Employee.Management.System.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerController
{
    @GetMapping("/dashboard")
    public ResponseEntity<String> managerDashboard() {
        return ResponseEntity.ok("Manager Dashboard: Access granted!");
    }
}
