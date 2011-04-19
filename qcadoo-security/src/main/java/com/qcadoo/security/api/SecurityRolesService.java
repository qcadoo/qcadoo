package com.qcadoo.security.api;


public interface SecurityRolesService {

    SecurityRole getRole(String roleIdetifier);

    boolean canAccess(SecurityRole targetRole);

    boolean canAccess(String targetRoleIdetifier);

}
