package com.example.Employee.Management.System.security;

public enum ApplicationUserPermission
{
    COMPANY_READ("company:read"),
    COMPANY_WRITE("company:write"),

    DEPARTMENT_WRITE("department:write"),
    DEPARTMENT_READ("department:read"),

    EMPLOYEE_WRITE("employee:write"),
    EMPLOYEE_READ("employee:read");

    private final String permission;

    ApplicationUserPermission(String permission)
    {
        this.permission = permission;
    }

    public String getPermission()
    {
        return permission;
    }
}
