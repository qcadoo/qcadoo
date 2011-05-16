package com.qcadoo.security.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.security.internal.role.InternalSecurityRolesService;

public class UserGroupModuleFactory extends ModuleFactory<UserGroupModule> {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Autowired
    private InternalSecurityRolesService securityRolesService;

    @Override
    protected UserGroupModule parseElement(final String pluginIdentifier, final Element element) {
        String name = getRequiredAttribute(element, "name");
        String role = getRequiredAttribute(element, "role");

        return new UserGroupModule(name, role, dataDefinitionService, securityRolesService);
    }

    @Override
    public String getIdentifier() {
        return "user-group";
    }

}
