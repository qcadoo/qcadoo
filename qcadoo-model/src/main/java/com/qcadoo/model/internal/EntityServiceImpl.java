/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.6
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.ExpressionService;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.types.BelongsToType;
import com.qcadoo.model.api.types.HasManyType;
import com.qcadoo.model.api.types.TreeType;
import com.qcadoo.model.internal.api.EntityService;
import com.qcadoo.model.internal.api.HibernateService;
import com.qcadoo.model.internal.api.InternalDataDefinition;
import com.qcadoo.model.internal.api.InternalFieldDefinition;
import com.qcadoo.model.internal.types.PasswordType;

@Service
public final class EntityServiceImpl implements EntityService {

    @Autowired
    private HibernateService hibernateService;

    @Autowired
    private ExpressionService expressionService;

    @Override
    public Long getId(final Object databaseEntity) {
        return (Long) getField(databaseEntity, FIELD_ID);
    }

    public Boolean getActive(final Object databaseEntity) {
        return (Boolean) getField(databaseEntity, FIELD_ACTIVE);
    }

    public void setActive(final Object databaseEntity, final boolean active) {
        setField(databaseEntity, FIELD_ACTIVE, active);
    }

    @Override
    public void setId(final Object databaseEntity, final Long id) {
        setField(databaseEntity, FIELD_ID, id);
    }

    @Override
    public void setField(final Object databaseEntity, final FieldDefinition fieldDefinition, final Object value) {
        if (!((InternalFieldDefinition) fieldDefinition).isEnabled()) {
            return;
        } else if (fieldDefinition.getType() instanceof PasswordType && value == null) {
            return;
        } else if (fieldDefinition.getType() instanceof BelongsToType && value != null) {
            Object belongsToValue = getBelongsToFieldValue(
                    (InternalDataDefinition) ((BelongsToType) fieldDefinition.getType()).getDataDefinition(), value);
            setField(databaseEntity, fieldDefinition.getName(), belongsToValue);
        } else if (fieldDefinition.getType() instanceof HasManyType) {
            setField(databaseEntity, fieldDefinition.getName(), null);
        } else if (fieldDefinition.getType() instanceof TreeType) {
            setField(databaseEntity, fieldDefinition.getName(), null);
        } else {
            setField(databaseEntity, fieldDefinition.getName(), value);
        }
    }

    private Object getBelongsToFieldValue(final InternalDataDefinition dataDefinition, final Object value) {
        Long id = null;

        if (value instanceof Long) {
            id = (Long) value;
        } else if (value instanceof Entity) {
            id = ((Entity) value).getId();
        } else {
            id = Long.parseLong(value.toString());
        }

        Class<?> referencedClass = dataDefinition.getClassForEntity();
        return hibernateService.getCurrentSession().load(referencedClass, id);
    }

    @Override
    public Object getField(final Object databaseEntity, final FieldDefinition fieldDefinition) {
        if (!((InternalFieldDefinition) fieldDefinition).isEnabled()) {
            return null;
        } else if (fieldDefinition.getType() instanceof BelongsToType) {
            return getBelongsToField(databaseEntity, fieldDefinition);
        } else if (fieldDefinition.getType() instanceof HasManyType) {
            return getHasManyField(databaseEntity, fieldDefinition);
        } else if (fieldDefinition.getType() instanceof TreeType) {
            return getTreeField(databaseEntity, fieldDefinition);
        } else {
            return getPrimitiveField(databaseEntity, fieldDefinition);
        }
    }

    @Override
    public Entity convertToGenericEntity(final InternalDataDefinition dataDefinition, final Object databaseEntity) {
        Entity genericEntity = null;

        if (databaseEntity instanceof Object[]) {
            genericEntity = dataDefinition.create();
            Object[] databaseArray = (Object[]) databaseEntity;

            List<String> fields = new ArrayList<String>(dataDefinition.getFields().keySet());

            for (int i = 0; i < fields.size(); i++) {
                if (dataDefinition.getField(fields.get(i)).getType() instanceof BelongsToType) {
                    InternalDataDefinition referencedDataDefinition = (InternalDataDefinition) ((BelongsToType) dataDefinition
                            .getField(fields.get(i)).getType()).getDataDefinition();
                    genericEntity.setField(fields.get(i), convertToGenericEntity(referencedDataDefinition, databaseArray[i]));
                } else {
                    genericEntity.setField(fields.get(i), databaseArray[i]);
                }
            }
        } else if (!(databaseEntity.getClass().getName().startsWith("com.qcadoo.model.beans"))) {
            genericEntity = new DefaultEntity(dataDefinition);
            genericEntity.setField(dataDefinition.getFields().keySet().iterator().next(), databaseEntity);
        } else {
            genericEntity = dataDefinition.create(getId(databaseEntity));

            if (dataDefinition.isActivable()) {
                genericEntity.setActive(getActive(databaseEntity));
            }

            for (Entry<String, FieldDefinition> fieldDefinitionEntry : dataDefinition.getFields().entrySet()) {
                if (fieldDefinitionEntry.getValue().isPersistent()
                        && ((InternalFieldDefinition) fieldDefinitionEntry.getValue()).isEnabled()) {
                    genericEntity.setField(fieldDefinitionEntry.getKey(),
                            getField(databaseEntity, fieldDefinitionEntry.getValue()));
                }
            }

            if (dataDefinition.isPrioritizable()) {
                genericEntity.setField(dataDefinition.getPriorityField().getName(),
                        getField(databaseEntity, dataDefinition.getPriorityField()));
            }

            for (Entry<String, FieldDefinition> fieldDefinitionEntry : dataDefinition.getFields().entrySet()) {
                if (fieldDefinitionEntry.getValue().getExpression() != null
                        && ((InternalFieldDefinition) fieldDefinitionEntry.getValue()).isEnabled()) {
                    genericEntity.setField(fieldDefinitionEntry.getKey(), expressionService.getValue(genericEntity,
                            fieldDefinitionEntry.getValue().getExpression(), Locale.ENGLISH));
                }
            }

            dataDefinition.callViewHook(genericEntity);
        }

        return genericEntity;
    }

    @Override
    public Object convertToDatabaseEntity(final InternalDataDefinition dataDefinition, final Entity genericEntity,
            final Object existingDatabaseEntity) {
        Object databaseEntity = getDatabaseEntity(dataDefinition, genericEntity, existingDatabaseEntity);

        for (Entry<String, FieldDefinition> fieldDefinitionEntry : dataDefinition.getFields().entrySet()) {
            if (fieldDefinitionEntry.getValue().isPersistent()
                    && ((InternalFieldDefinition) fieldDefinitionEntry.getValue()).isEnabled()) {
                setField(databaseEntity, fieldDefinitionEntry.getValue(), genericEntity.getField(fieldDefinitionEntry.getKey()));
            }
        }

        if (dataDefinition.isPrioritizable() && genericEntity.getField(dataDefinition.getPriorityField().getName()) != null) {
            setField(databaseEntity, dataDefinition.getPriorityField(),
                    genericEntity.getField(dataDefinition.getPriorityField().getName()));
        }

        if (dataDefinition.isActivable()) {
            setActive(databaseEntity, genericEntity.isActive());
        }

        return databaseEntity;
    }

    private Object getDatabaseEntity(final InternalDataDefinition dataDefinition, final Entity genericEntity,
            final Object existingDatabaseEntity) {
        Object databaseEntity = null;

        if (existingDatabaseEntity != null) {
            databaseEntity = existingDatabaseEntity;
        } else {
            databaseEntity = dataDefinition.getInstanceForEntity();
            setId(databaseEntity, genericEntity.getId());
        }
        return databaseEntity;
    }

    private Object getPrimitiveField(final Object databaseEntity, final FieldDefinition fieldDefinition) {
        return getField(databaseEntity, fieldDefinition.getName());
    }

    private Object getHasManyField(final Object databaseEntity, final FieldDefinition fieldDefinition) {
        Long parentId = getId(databaseEntity);
        HasManyType hasManyFieldType = (HasManyType) fieldDefinition.getType();
        InternalDataDefinition referencedDataDefinition = (InternalDataDefinition) hasManyFieldType.getDataDefinition();

        return new EntityListImpl(referencedDataDefinition, hasManyFieldType.getJoinFieldName(), parentId);
    }

    private Object getTreeField(final Object databaseEntity, final FieldDefinition fieldDefinition) {
        Long parentId = getId(databaseEntity);
        TreeType treeFieldType = (TreeType) fieldDefinition.getType();
        InternalDataDefinition referencedDataDefinition = (InternalDataDefinition) treeFieldType.getDataDefinition();

        return new EntityTreeImpl(referencedDataDefinition, treeFieldType.getJoinFieldName(), parentId);
    }

    private Object getBelongsToField(final Object databaseEntity, final FieldDefinition fieldDefinition) {
        BelongsToType belongsToFieldType = (BelongsToType) fieldDefinition.getType();
        InternalDataDefinition referencedDataDefinition = (InternalDataDefinition) belongsToFieldType.getDataDefinition();

        Object value = getField(databaseEntity, fieldDefinition.getName());

        if (value == null) {
            return null;
        }

        if (belongsToFieldType.isLazyLoading()) {
            Long id = null;

            if (value instanceof HibernateProxy) {
                id = (Long) ((HibernateProxy) value).getHibernateLazyInitializer().getIdentifier();
            } else {
                id = getId(getField(databaseEntity, fieldDefinition.getName()));
            }

            if (id == null) {
                return null;
            }

            return new ProxyEntity(referencedDataDefinition, id);
        } else {
            return convertToGenericEntity(referencedDataDefinition, value);
        }
    }

    private void setField(final Object databaseEntity, final String fieldName, final Object value) {
        try {
            PropertyUtils.setProperty(databaseEntity, fieldName, value);
        } catch (Exception e) {
            throw new IllegalStateException("cannot set value of the property: " + databaseEntity.getClass().getSimpleName()
                    + ", " + fieldName, e);
        }
    }

    private Object getField(final Object databaseEntity, final String fieldName) {
        try {
            return PropertyUtils.getProperty(databaseEntity, fieldName);
        } catch (Exception e) {
            throw new IllegalStateException("cannot get value of the property: " + databaseEntity.getClass().getSimpleName()
                    + ", " + fieldName, e);
        }
    }

}
