package com.qcadoo.security.internal.role;

import com.qcadoo.security.api.SecurityRole;
import com.qcadoo.security.api.SecurityRolesService;

public interface InternalSecurityRolesService extends SecurityRolesService {

    void addRole(SecurityRole role);

}
