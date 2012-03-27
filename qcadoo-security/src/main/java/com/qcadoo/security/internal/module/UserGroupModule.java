/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.4
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
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

    @Override
    public void enable() {
        enableOnStartup();
    }

}
