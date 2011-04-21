package com.qcadoo.security.api;

/**
 * @since 0.4.0
 */
public interface SecurityViewDefinitionRoleResolver {

    SecurityRole getRoleForView(String pluginIdentifier, String viewName);

}
