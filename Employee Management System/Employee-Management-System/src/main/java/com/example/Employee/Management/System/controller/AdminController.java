package com.example.Employee.Management.System.controller;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController
{
    @GetMapping("/")
    public ResponseEntity<String> admin(Authentication authentication)
    {
        String username = authentication.getName();
        return ResponseEntity.ok("Welcome " + username + "!");
    }
}
