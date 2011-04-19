package com.qcadoo.security.internal.role;

import org.springframework.security.core.Authentication;

import com.qcadoo.security.api.SecurityRole;
import com.qcadoo.security.api.SecurityRolesService;

public interface InternalSecurityRolesService extends SecurityRolesService {

    void addRole(SecurityRole role);

    boolean canAccess(Authentication userAuthentication, SecurityRole targetRole);

}
