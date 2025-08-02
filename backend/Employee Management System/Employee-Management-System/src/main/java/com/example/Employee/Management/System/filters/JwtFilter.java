package com.example.Employee.Management.System.filters;

import com.example.Employee.Management.System.service.CustomUserDetailsService;
import com.example.Employee.Management.System.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** we want this class to behave like a filter
 * AuthorizationFilter
 * **/
@Component
public class JwtFilter extends OncePerRequestFilter
{

    private final JWTService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtFilter(JWTService jwtService, CustomUserDetailsService customUserDetailsService)
    {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        // Will get Bearer Token
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
        {
            // we start from 7 index as (Bearer token), the token starts at index 7 after Bearer
            token = authorizationHeader.substring(7);

            if(jwtService.validateToken(token))
            {
                username = jwtService.getUsernameFromJwt(token);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
