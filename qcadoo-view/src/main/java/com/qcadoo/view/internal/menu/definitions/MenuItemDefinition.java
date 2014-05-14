package com.qcadoo.view.internal.menu.definitions;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class MenuItemDefinition {

    private final String pluginIdentifier;

    private final String name;

    private final String categoryName;

    private final String authRoleIdentifier;

    private final String viewPluginIdentifier;

    private final String viewName;

    private final String url;

    public static MenuItemDefinition create(final String pluginIdentifier, final String name, final String categoryName,
            final String authRoleIdentifier) {
        return new MenuItemDefinition(pluginIdentifier, name, categoryName, authRoleIdentifier, null, null, null);
    }

    public MenuItemDefinition forUrl(final String url) {
        return new MenuItemDefinition(pluginIdentifier, name, categoryName, authRoleIdentifier, pluginIdentifier, name, url);
    }

    public MenuItemDefinition forView(final String viewPlugin, final String viewName) {
        return new MenuItemDefinition(pluginIdentifier, name, categoryName, authRoleIdentifier, viewPlugin, viewName, null);
    }

    private MenuItemDefinition(final String pluginIdentifier, final String name, final String categoryName,
            final String authRoleIdentifier, final String viewPluginIdentifier, final String viewName, final String url) {
        this.pluginIdentifier = pluginIdentifier;
        this.name = name;
        this.categoryName = categoryName;
        this.authRoleIdentifier = authRoleIdentifier;
        this.viewPluginIdentifier = viewPluginIdentifier;
        this.viewName = viewName;
        this.url = url;
    }

    public String getAuthRoleIdentifier() {
        return authRoleIdentifier;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getName() {
        return name;
    }

    public String getPluginIdentifier() {
        return pluginIdentifier;
    }

    public String getViewPluginIdentifier() {
        return viewPluginIdentifier;
    }

    public String getViewName() {
        return viewName;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        MenuItemDefinition rhs = (MenuItemDefinition) obj;
        return new EqualsBuilder().append(this.pluginIdentifier, rhs.pluginIdentifier).append(this.name, rhs.name)
                .append(this.categoryName, rhs.categoryName).append(this.authRoleIdentifier, rhs.authRoleIdentifier).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(pluginIdentifier).append(name).append(categoryName).append(authRoleIdentifier)
                .toHashCode();
    }

}
