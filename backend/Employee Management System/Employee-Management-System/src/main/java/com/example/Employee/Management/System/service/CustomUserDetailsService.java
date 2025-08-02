package com.example.Employee.Management.System.service;

import com.example.Employee.Management.System.models.User;
import com.example.Employee.Management.System.models.UserPrincipal;
import com.example.Employee.Management.System.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService
{
    private final UserRepo userRepository; // Ensure this is correctly wired
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        logger.info("Load User by Username: {}", username);
        logger.debug("Loaded user: {}  with password hash: {}", username, user.getPassword());
        return new UserPrincipal(user);
    }


}
