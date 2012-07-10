/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.6
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.model.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.EntityList;
import com.qcadoo.model.api.EntityTree;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.validators.ErrorMessage;
import com.qcadoo.model.internal.api.EntityAwareCopyPerformer;
import com.qcadoo.model.internal.api.EntityAwareEqualsPerformer;

public final class ProxyEntity implements Entity, EntityAwareCopyPerformer, EntityAwareEqualsPerformer {

    private final DataDefinition dataDefinition;

    private final Long id;

    private Entity entity = null;

    public ProxyEntity(final DataDefinition dataDefinition, final Long id) {
        checkNotNull(id, "missing id for proxied entity");
        this.dataDefinition = dataDefinition;
        this.id = id;
    }

    private Entity getEntity() {
        if (entity == null) {
            entity = dataDefinition.get(id);
            checkNotNull(entity, "Proxy can't load entity");
        }
        return entity;
    }

    @Override
    public void setId(final Long id) {
        getEntity().setId(id);
    }

    @Override
    public Long getId() {
        if (entity == null) {
            return id;
        } else {
            return entity.getId();
        }
    }

    @Override
    public Object getField(final String fieldName) {
        return getEntity().getField(fieldName);
    }

    @Override
    public void setField(final String fieldName, final Object fieldValue) {
        getEntity().setField(fieldName, fieldValue);
    }

    @Override
    public Map<String, Object> getFields() {
        return getEntity().getFields();
    }

    @Override
    public void addGlobalError(final String message, final String... vars) {
        getEntity().addGlobalError(message, vars);
    }

    @Override
    public void addError(final FieldDefinition fieldDefinition, final String message, final String... vars) {
        getEntity().addError(fieldDefinition, message, vars);
    }

    @Override
    public List<ErrorMessage> getGlobalErrors() {
        return getEntity().getGlobalErrors();
    }

    @Override
    public Map<String, ErrorMessage> getErrors() {
        return getEntity().getErrors();
    }

    @Override
    public ErrorMessage getError(final String fieldName) {
        return getEntity().getError(fieldName);
    }

    @Override
    public boolean isValid() {
        return getEntity().isValid();
    }

    @Override
    public void setNotValid() {
        getEntity().setNotValid();
    }

    @Override
    public boolean isFieldValid(final String fieldName) {
        return getEntity().isFieldValid(fieldName);
    }

    @Override
    public boolean isActive() {
        return getEntity().isActive();
    }

    @Override
    public void setActive(final boolean active) {
        getEntity().setActive(active);
    }

    @Override
    public Entity copy() {
        return copy(null);
    }

    @Override
    public Entity copy(final Entity performerEntity) {
        Entity entityCopy = null;
        if (getEntity() instanceof EntityAwareCopyPerformer) {
            entityCopy = ((EntityAwareCopyPerformer) getEntity()).copy(performerEntity);
        } else {
            entityCopy = getEntity().copy();
        }
        return entityCopy;
    }

    @Override
    public String getStringField(final String fieldName) {
        return getEntity().getStringField(fieldName);
    }

    @Override
    public boolean getBooleanField(final String fieldName) {
        return getEntity().getBooleanField(fieldName);
    }

    @Override
    public BigDecimal getDecimalField(final String fieldName) {
        return getEntity().getDecimalField(fieldName);
    }

    @Override
    public Entity getBelongsToField(final String fieldName) {
        return getEntity().getBelongsToField(fieldName);
    }

    @Override
    public EntityList getHasManyField(final String fieldName) {
        return getEntity().getHasManyField(fieldName);
    }

    @Override
    public List<Entity> getManyToManyField(final String fieldName) {
        return getEntity().getManyToManyField(fieldName);
    }

    @Override
    public EntityTree getTreeField(final String fieldName) {
        return getEntity().getTreeField(fieldName);
    }

    @Override
    public DataDefinition getDataDefinition() {
        return dataDefinition;
    }

    @Override
    public String toString() {
        return "EntityProxy[" + dataDefinition.getPluginIdentifier() + "." + dataDefinition.getName() + "][id=" + id + "]";
    }

    @Override
    public int hashCode() {
        return getEntity().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Entity)) {
            return false;
        }
        Entity other = (Entity) obj;

        if (!definitionsAreEquals(other)) {
            return false;
        }

        return getEntity().equals(other);
    }

    private boolean definitionsAreEquals(final Entity other) {
        return new EqualsBuilder().append(id, other.getId()).append(dataDefinition, other.getDataDefinition()).isEquals();
    }

    @Override
    public boolean equals(final Entity obj, final Entity performerEntity) {
        boolean isEquals;
        final Entity entity = getEntity();
        if (entity instanceof EntityAwareEqualsPerformer) {
            isEquals = ((EntityAwareEqualsPerformer) entity).equals(obj, performerEntity);
        } else {
            isEquals = entity.equals(obj);
        }
        return isEquals;
    }

    @Override
    public boolean flatEquals(final Entity obj) {
        boolean isEquals;
        final Entity entity = getEntity();
        if (entity instanceof EntityAwareEqualsPerformer) {
            isEquals = ((EntityAwareEqualsPerformer) entity).flatEquals(obj);
        } else {
            isEquals = entity.equals(obj);
        }
        return isEquals;
    }

}
