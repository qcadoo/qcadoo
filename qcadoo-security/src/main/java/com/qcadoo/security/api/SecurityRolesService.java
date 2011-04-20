package com.qcadoo.security.api;

public interface SecurityRolesService {

    SecurityRole getRoleByIdentifier(String roleIdetifier);

    SecurityRole getRoleByName(String roleName);

    boolean canAccess(SecurityRole targetRole);

    boolean canAccess(String targetRoleIdetifier);

}
