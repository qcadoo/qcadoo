package com.qcadoo.security.api;

/**
 * @since 0.4.0
 */
public interface SecurityRole {

    String getName();

    String getRoleIdentifier();

    boolean isAccessible();

}
