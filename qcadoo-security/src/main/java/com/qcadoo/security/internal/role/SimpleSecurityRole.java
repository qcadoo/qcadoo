package com.qcadoo.security.internal.role;

import com.qcadoo.security.api.SecurityRole;

public class SimpleSecurityRole implements SecurityRole {

    private final String name;

    private final String roleIdentifier;

    public SimpleSecurityRole(String name, String roleIdentifier) {
        this.name = name;
        this.roleIdentifier = roleIdentifier;
    }

    public String getName() {
        return name;
    }

    public String getRoleIdentifier() {
        return roleIdentifier;
    }

    @Override
    public String toString() {
        return "SimpleSecurityRole [name=" + name + ", roleIdentifier=" + roleIdentifier + "]";
    }

}
