package com.example.Employee.Management.System.security;


import com.example.Employee.Management.System.models.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.example.Employee.Management.System.models.Role.*;

@Configuration

public class ApplicationSecurityConfig
{


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/", "/default", "/index.html") // Allow access for everyone even if he/she does not login
                        .permitAll()

                        .requestMatchers("/admin/**")
                        .hasRole(ADMIN.name())

                        .requestMatchers("/manager/**")
                        .hasRole(MANAGER.name())

                        .requestMatchers("/employee/**")
                        .hasAnyRole(EMPLOYEE.name(), MANAGER.name(), ADMIN.name())
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {})
                .csrf(csrf -> csrf.disable());
        return http.build();
    }


    // Add test user for authorization
    @Bean
    public UserDetailsService userDetailsService()
    {
                UserDetails admin = User.withUsername("ashraf")
                .password(passwordEncoder().encode("ashraf25102000A"))
                .roles(ADMIN.name())
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
