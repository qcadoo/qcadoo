package com.qcadoo.security.internal.role;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.qcadoo.security.api.SecurityRole;

@Service
public class InternalSecurityRolesServiceImpl implements InternalSecurityRolesService {

    private final Map<String, SecurityRole> roles = new HashMap<String, SecurityRole>();

    @Override
    public SecurityRole getRole(String roleIdetifier) {
        return roles.get(roleIdetifier);
    }

    @Override
    public void addRole(SecurityRole role) {
        roles.put(role.getRoleIdentifier(), role);

    }

}
