package com.example.Employee.Management.System.controller;


import com.example.Employee.Management.System.models.User;
import com.example.Employee.Management.System.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignupController
{
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        // Encode password, set default role if needed, etc.
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "Signup successful! Please login.");
        return "redirect:/login";
    }
}
