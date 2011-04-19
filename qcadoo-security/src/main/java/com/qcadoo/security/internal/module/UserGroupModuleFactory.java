package com.qcadoo.security.internal.module;

import static com.google.common.base.Preconditions.checkNotNull;

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
    public UserGroupModule parse(final String pluginIdentifier, final Element element) {
        String name = element.getAttributeValue("name");
        String role = element.getAttributeValue("role");

        checkNotNull(name, "Missing name attribute of " + getIdentifier() + " module");
        checkNotNull(role, "Missing role attribute of " + getIdentifier() + " module");

        return new UserGroupModule(name, role, dataDefinitionService, securityRolesService);
    }

    @Override
    public String getIdentifier() {
        return "user-group";
    }

}
