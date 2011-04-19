package com.qcadoo.security.internal.role;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.qcadoo.security.api.SecurityRole;

@Service
public class InternalSecurityRolesServiceImpl implements InternalSecurityRolesService {

    @Autowired
    private RoleHierarchy roleHierarchy;

    private final Map<String, SecurityRole> roles = new HashMap<String, SecurityRole>();

    @Override
    public SecurityRole getRole(final String roleIdetifier) {
        return roles.get(roleIdetifier);
    }

    @Override
    public void addRole(final SecurityRole role) {
        roles.put(role.getRoleIdentifier(), role);
    }

    @Override
    public boolean canAccess(final String targetRoleIdetifier) {
        Preconditions.checkNotNull(targetRoleIdetifier, "targetRoleIdetifier must be not null");
        SecurityRole targetRole = getRole(targetRoleIdetifier);
        Preconditions.checkState(targetRoleIdetifier != null, "No such role '" + targetRoleIdetifier + "'");
        return canAccess(targetRole);
    }

    @Override
    public boolean canAccess(final SecurityRole targetRole) {
        Preconditions.checkState(SecurityContextHolder.getContext() != null, "No security context");
        Preconditions.checkState(SecurityContextHolder.getContext().getAuthentication() != null,
                "No authentication in security context");
        return canAccess(SecurityContextHolder.getContext().getAuthentication(), targetRole);
    }

    @Override
    public boolean canAccess(final Authentication userAuthentication, final SecurityRole targetRole) {
        Preconditions.checkNotNull(userAuthentication, "userAuthentication must be not null");

        if (targetRole == null) {
            return true;
        }

        Collection<GrantedAuthority> reachableAuthorities = roleHierarchy.getReachableGrantedAuthorities(userAuthentication
                .getAuthorities());

        for (GrantedAuthority grantedAuthority : reachableAuthorities) {
            if (grantedAuthority.getAuthority().equals(targetRole.getRoleIdentifier())) {
                return true;
            }
        }

        return false;
    }

}
