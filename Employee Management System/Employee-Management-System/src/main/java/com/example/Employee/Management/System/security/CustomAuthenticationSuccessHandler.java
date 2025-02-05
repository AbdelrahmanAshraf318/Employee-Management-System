package com.example.Employee.Management.System.security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Check roles and redirect accordingly
        authentication.getAuthorities().forEach(authority -> {
            try {
                if(authority.getAuthority().equals("ROLE_ADMIN")){
                    response.sendRedirect("/admin/dashboard");
                } else if(authority.getAuthority().equals("ROLE_MANAGER")){
                    response.sendRedirect("/manager/dashboard");
                } else if(authority.getAuthority().equals("ROLE_EMPLOYEE")){
                    response.sendRedirect("/employee/dashboard");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
