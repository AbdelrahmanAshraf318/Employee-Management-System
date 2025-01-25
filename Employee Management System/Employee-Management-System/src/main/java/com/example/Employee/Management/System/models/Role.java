package com.example.Employee.Management.System.models;

import com.example.Employee.Management.System.security.ApplicationUserPermission;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Set;

import static com.example.Employee.Management.System.security.ApplicationUserPermission.*;

public enum Role
{
    ADMIN(Sets.newHashSet(COMPANY_READ, COMPANY_WRITE, DEPARTMENT_WRITE, DEPARTMENT_READ,
            EMPLOYEE_READ, EMPLOYEE_WRITE)),
    MANAGER(Sets.newHashSet()),
    EMPLOYEE(Sets.newHashSet());

    private final Set<ApplicationUserPermission> permissions;

    Role(Set<ApplicationUserPermission> permissions)
    {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions()
    {
        return permissions;
    }
}
