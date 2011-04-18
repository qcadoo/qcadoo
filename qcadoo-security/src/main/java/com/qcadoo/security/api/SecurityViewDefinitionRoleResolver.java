package com.qcadoo.security.api;

public interface SecurityViewDefinitionRoleResolver {

    SecurityRole getRoleForView(String pluginIdentifier, String viewName);

}
