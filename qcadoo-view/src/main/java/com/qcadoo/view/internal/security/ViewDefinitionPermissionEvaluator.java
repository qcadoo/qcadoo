package com.qcadoo.view.internal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;

import com.qcadoo.security.api.SecurityRole;
import com.qcadoo.security.internal.permissionEvaluators.QcadooPermisionEvaluator;
import com.qcadoo.security.internal.role.InternalSecurityRolesService;

public class ViewDefinitionPermissionEvaluator implements QcadooPermisionEvaluator {

    // Need to use late initialization - probably because ViewDefinitionService is authorized
    @Autowired
    private ApplicationContext context;

    private SecurityViewDefinitionRoleResolver viewDefinitionRoleResolver;

    @Autowired
    private InternalSecurityRolesService securityRolesService;

    @Override
    public String getTargetType() {
        return "viewDefinition";
    }

    @Override
    public boolean hasPermission(Authentication authentication, String permission, String targetId) {

        if ("isAuthorizedToSee".equals(permission)) {

            SecurityRole role = getViewRole(targetId);

            return securityRolesService.canAccess(authentication, role);

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
