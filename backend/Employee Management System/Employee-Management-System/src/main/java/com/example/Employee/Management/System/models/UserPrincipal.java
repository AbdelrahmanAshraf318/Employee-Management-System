package com.example.Employee.Management.System.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

// This class for fetching the current user who logins to the system
public class UserPrincipal implements UserDetails
{

    private final User user;

    public UserPrincipal(User user)
    {
        this.user = user;
    }

    // this for role authorization
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Collections.singleton( new SimpleGrantedAuthority( user.getRole().toString() ));
    }

    @Override
    public String getPassword()
    {
        return user.getPassword();
    }

    @Override
    public String getUsername()
    {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
