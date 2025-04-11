package com.example.Employee.Management.System.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerController
{
    @GetMapping("/")
    public ResponseEntity<String> greetManager(Authentication authentication)
    {
        String username = authentication.getName();
        return ResponseEntity.ok("Welcome " + username + "!");
    }
}
