package com.qcadoo.model.internal.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;

public final class EntitySignature {

    private final String plugin;

    private final String model;

    private final Long id;

    public static EntitySignature of(final Entity entity) {
        DataDefinition dataDefinition = entity.getDataDefinition();
        return new EntitySignature(dataDefinition.getPluginIdentifier(), dataDefinition.getName(), entity.getId());
    }

    private EntitySignature(final String plugin, final String model, final Long id) {
        this.plugin = plugin;
        this.model = model;
        this.id = id;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1, 31).append(id).append(model).append(plugin).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EntitySignature other = (EntitySignature) obj;
        return new EqualsBuilder().append(id, other.id).append(model, other.model).append(plugin, other.plugin).isEquals();
    }

    @Override
    public String toString() {
        return String.format("%s.%s[id=%s]", plugin, model, id);
    }
}
