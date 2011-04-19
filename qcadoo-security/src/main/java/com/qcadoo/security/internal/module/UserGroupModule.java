package com.qcadoo.security.internal.module;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.Restrictions;
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
        multiTenantEnable();
    }

    @Override
    public void multiTenantEnableOnStartup() {
        multiTenantEnable();
    }

    @Override
    public void multiTenantEnable() {

        securityRolesService.addRole(new SimpleSecurityRole(name, role));

        if (dataDefinitionService.get("qcadooSecurity", "group").find().addRestriction(Restrictions.eq("name", name)).list()
                .getTotalNumberOfEntities() > 0) {
            return;
        }

        Entity entity = dataDefinitionService.get("qcadooSecurity", "group").create();
        entity.setField("name", name);
        entity.setField("role", role);
        dataDefinitionService.get("qcadooSecurity", "group").save(entity);
    }
}
