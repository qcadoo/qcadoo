package com.qcadoo.security.api;

/**
 * @since 0.4.0
 */
public interface SecurityRolesService {

    SecurityRole getRoleByIdentifier(String roleIdetifier);

    SecurityRole getRoleByName(String roleName);

    boolean canAccess(SecurityRole targetRole);

    boolean canAccess(String targetRoleIdetifier);

}
