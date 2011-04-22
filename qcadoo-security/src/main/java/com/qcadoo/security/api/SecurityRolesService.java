package com.qcadoo.security.api;

/**
 * Service for getting defined roles and role-based access
 * 
 * @since 0.4.0
 */
public interface SecurityRolesService {

    /**
     * returns role with defined identifier or null if no such role found
     * 
     * @param roleIdetifier
     *            identifier of role
     * @return found role or null
     */
    SecurityRole getRoleByIdentifier(String roleIdetifier);

    /**
     * returns role with defined name or null if no such role found
     * 
     * @param roleName
     *            name of role
     * @return found role or null
     */
    SecurityRole getRoleByName(String roleName);

    /**
     * Checks if current user can access resource with specified access role
     * 
     * @param targetRole
     *            resource access role
     * @return true if current user can access to defined role, false otherwise
     */
    boolean canAccess(SecurityRole targetRole);

    /**
     * Checks if current user can access resource with specified access role identifier
     * 
     * @param targetRoleIdetifier
     *            resource access role identifier
     * @return true if current user can access to defined role, false otherwise
     */
    boolean canAccess(String targetRoleIdetifier);

}
