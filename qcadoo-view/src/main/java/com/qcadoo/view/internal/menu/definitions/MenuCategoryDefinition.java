package com.qcadoo.view.internal.menu.definitions;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class MenuCategoryDefinition {

    private final String pluginIdentifier;

    private final String name;

    private final String authRole;

    public MenuCategoryDefinition(final String pluginIdentifier, final String name, final String authRole) {
        this.pluginIdentifier = pluginIdentifier;
        this.name = name;
        this.authRole = authRole;
    }

    public String getPluginIdentifier() {
        return pluginIdentifier;
    }

    public String getName() {
        return name;
    }

    public String getAuthRole() {
        return authRole;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuCategoryDefinition that = (MenuCategoryDefinition) o;
        return new EqualsBuilder().append(name, that.name).append(pluginIdentifier, that.pluginIdentifier)
                .append(authRole, that.authRole).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(pluginIdentifier).toHashCode();
    }
}
