package com.qcadoo.security.internal.module;

import java.util.List;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.internal.types.EnumType;
import com.qcadoo.model.internal.types.EnumTypeKey;
import com.qcadoo.plugin.api.Module;
import com.qcadoo.security.internal.role.InternalSecurityRolesService;
import com.qcadoo.security.internal.role.SimpleSecurityRole;

public class UserGroupModule extends Module {

    private final String name;

    private final String role;

    private final DataDefinitionService dataDefinitionService;

    private final InternalSecurityRolesService securityRolesService;

    public UserGroupModule(final String name, final String role, final DataDefinitionService dataDefinitionService,
            final InternalSecurityRolesService securityRolesService) {
        this.name = name;
        this.role = role;
        this.dataDefinitionService = dataDefinitionService;
        this.securityRolesService = securityRolesService;
    }

    @Override
    public void enableOnStartup() {
        DataDefinition userDataDefinition = dataDefinitionService.get("qcadooSecurity", "user");
        List<EnumTypeKey> userModelRoles = ((EnumType) userDataDefinition.getField("role").getType()).getKeys();
        userModelRoles.add(new EnumTypeKey(name, null));

        securityRolesService.addRole(new SimpleSecurityRole(name, role, true));
    }
}
