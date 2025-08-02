package com.example.Employee.Management.System.filters;

import com.example.Employee.Management.System.dtos.LoginRequestDTO;
import com.example.Employee.Management.System.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter
{
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(JwtLoginFilter.class);

    public JwtLoginFilter(AuthenticationManager authenticationManager,
                          JWTService jwtService)
    {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException
    {
        try
        {
            logger.info("Attempting authentication");

            LoginRequestDTO loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);

            logger.info("Extracted credentials - Username: '{}', Password: '{}'",
                    loginRequest.getUsername(), loginRequest.getPassword());

            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    );

            return authenticationManager.authenticate(authRequest);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Invalid login request format", e);
        }
        catch (java.io.IOException e)
        {
            throw new RuntimeException("I/O error during authentication", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException, java.io.IOException
    {
        String token = jwtService.generateToken(authResult);
        response.addHeader("Authorization", "Bearer " + token);
    }

}
