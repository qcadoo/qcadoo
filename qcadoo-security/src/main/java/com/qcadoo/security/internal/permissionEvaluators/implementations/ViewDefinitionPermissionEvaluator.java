package com.qcadoo.security.internal.permissionEvaluators.implementations;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.qcadoo.security.api.SecurityRole;
import com.qcadoo.security.api.SecurityViewDefinitionRoleResolver;
import com.qcadoo.security.internal.permissionEvaluators.QcadooPermisionEvaluator;

public class ViewDefinitionPermissionEvaluator implements QcadooPermisionEvaluator {

    // Need to use late initialization - probably because ViewDefinitionService is authorized
    @Autowired
    private ApplicationContext context;

    private SecurityViewDefinitionRoleResolver viewDefinitionRoleResolver;

    @Autowired
    private RoleHierarchy roleHierarchy;

    @Override
    public String getTargetType() {
        return "viewDefinition";
    }

    @Override
    public boolean hasPermission(Authentication authentication, String permission, String targetId) {

        if ("isAuthorizedToSee".equals(permission)) {

            SecurityRole role = getViewRole(targetId);

            if (role == null) {
                return true;
            }
            Collection<GrantedAuthority> reachableAuthorities = roleHierarchy.getReachableGrantedAuthorities(authentication
                    .getAuthorities());

            for (GrantedAuthority grantedAuthority : reachableAuthorities) {
                if (grantedAuthority.getAuthority().equals(role.getRoleIdentifier())) {
                    return true;
                }
            }

            return false;

        } else {
            throw new IllegalArgumentException("permission type '" + permission + "' not supported");
        }
    }

    private SecurityRole getViewRole(String targetId) {
        if (viewDefinitionRoleResolver == null) {
            viewDefinitionRoleResolver = context.getBean(SecurityViewDefinitionRoleResolver.class);
        }
        String[] viewNameParts = targetId.split("#");
        return viewDefinitionRoleResolver.getRoleForView(viewNameParts[0], viewNameParts[1]);
    }

}
