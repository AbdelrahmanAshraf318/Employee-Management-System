package com.example.Employee.Management.System.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // Determine target URL based on role
        String targetUrl = determineTargetUrl(authentication.getAuthorities());

        // Redirect to the target URL
        response.sendRedirect(targetUrl);
    }

    private String determineTargetUrl(Collection<? extends GrantedAuthority> authorities) {
        String targetUrl = "/home"; // Fallback URL
        for (GrantedAuthority authority : authorities)
        {
            String role = authority.getAuthority();
            if ("ROLE_ADMIN".equals(role))
            {
                targetUrl = "/admin/";
                break;
            }
            else if ("ROLE_USER".equals(role))
            {
                targetUrl = "/employee/";
                break;
            }
            else if("ROLE_MANAGER".equals(role))
            {
                targetUrl = "/manager/";
                break;
            }
            // Add more role checks as needed
        }
        return targetUrl;
    }
}
