package com.qcadoo.security.api;

/**
 * Defines single user role
 * 
 * @since 0.4.0
 */
public interface SecurityRole {

    /**
     * Returns name of role
     * 
     * @return name of this role
     */
    String getName();

    /**
     * Returns identifier of role
     * 
     * @return identifier of this role
     */
    String getRoleIdentifier();

    /**
     * Returns true if administrator is allowed to link this role to user
     * 
     * @return true if administrator is allowed to link this role to user
     */
    boolean isAccessibleForUsers();

}
