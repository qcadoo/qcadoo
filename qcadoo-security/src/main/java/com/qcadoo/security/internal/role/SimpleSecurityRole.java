package com.qcadoo.security.internal.role;

import com.qcadoo.security.api.SecurityRole;

public class SimpleSecurityRole implements SecurityRole {

    private final String name;

    private final String roleIdentifier;

    private final boolean isAccessible;

    public SimpleSecurityRole(String name, String roleIdentifier, final boolean isAccessible) {
        this.name = name;
        this.roleIdentifier = roleIdentifier;
        this.isAccessible = isAccessible;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRoleIdentifier() {
        return roleIdentifier;
    }

    @Override
    public boolean isAccessible() {
        return isAccessible;
    }

    @Override
    public String toString() {
        return "SimpleSecurityRole [name=" + name + ", roleIdentifier=" + roleIdentifier + "]";
    }

}
