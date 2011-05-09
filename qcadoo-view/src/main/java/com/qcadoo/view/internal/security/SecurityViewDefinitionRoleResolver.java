package com.qcadoo.view.internal.security;

import com.qcadoo.security.api.SecurityRole;

/**
 * Interface for service that will provide information about access role of view definitions.
 * 
 * @since 0.4.0
 */
public interface SecurityViewDefinitionRoleResolver {

    /**
     * Gets access role of view definition
     * 
     * @param pluginIdentifier
     *            plugin identifier of view definition
     * @param viewName
     *            name of view definition
     * @return security role of view definition - can be null if no role defined to this view
     */
    SecurityRole getRoleForView(String pluginIdentifier, String viewName);

}
