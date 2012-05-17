/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.5
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

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.types.BelongsToType;
import com.qcadoo.model.api.types.FieldType;
import com.qcadoo.model.api.types.HasManyType;
import com.qcadoo.model.api.types.ManyToManyType;
import com.qcadoo.model.api.types.TreeType;
import com.qcadoo.model.internal.api.InternalDataDefinition;
import com.qcadoo.model.internal.api.InternalFieldDefinition;
import com.qcadoo.model.internal.api.ValidationService;
import com.qcadoo.model.internal.api.ValueAndError;
import com.qcadoo.model.internal.types.PasswordType;

@Service
public final class ValidationServiceImpl implements ValidationService {

    @Override
    public void validateGenericEntity(final InternalDataDefinition dataDefinition, final Entity genericEntity,
            final Entity existingGenericEntity) {

        copyReadOnlyAndMissingFields(dataDefinition, genericEntity, existingGenericEntity);
        parseFields(dataDefinition, genericEntity);

        if (genericEntity.getId() == null) {
            dataDefinition.callCreateHook(genericEntity);
            parseAndValidateEntity(dataDefinition, genericEntity);
        } else {
            parseAndValidateEntity(dataDefinition, genericEntity);
            dataDefinition.callUpdateHook(genericEntity);
        }
        dataDefinition.callSaveHook(genericEntity);
    }

    private void copyReadOnlyAndMissingFields(final InternalDataDefinition dataDefinition, final Entity genericEntity,
            final Entity existingGenericEntity) {
        for (Map.Entry<String, FieldDefinition> field : dataDefinition.getFields().entrySet()) {
            Object value = null;
            if (existingGenericEntity != null) {
                value = existingGenericEntity.getField(field.getKey());
            }
            if (field.getValue().getType() instanceof PasswordType) {
                continue;
            }
            if (field.getValue().isReadOnly()) {
                genericEntity.setField(field.getKey(), value);
            }
            if (!genericEntity.getFields().containsKey(field.getKey()) && genericEntity.getId() != null) {
                genericEntity.setField(field.getKey(), value);
            }
        }
    }

    private void parseFields(final InternalDataDefinition dataDefinition, final Entity genericEntity) {
        for (Entry<String, FieldDefinition> fieldDefinitionEntry : dataDefinition.getFields().entrySet()) {
            final InternalFieldDefinition fieldDefinition = (InternalFieldDefinition) fieldDefinitionEntry.getValue();
            final FieldType fieldType = fieldDefinition.getType();
            final Object fieldValue = genericEntity.getField(fieldDefinitionEntry.getKey());
            Object parsedValue = null;

            if (fieldType instanceof BelongsToType) {
                parsedValue = parseBelongsToField(fieldDefinition, trimAndNullIfEmpty(fieldValue), genericEntity);
            } else {
                parsedValue = fieldValue;
            }

            genericEntity.setField(fieldDefinitionEntry.getKey(), parsedValue);
        }
    }

    private Object parseBelongsToField(final InternalFieldDefinition fieldDefinition, final Object value,
            final Entity validatedEntity) {
        Entity referencedEntity = null;

        if (value != null) {
            Long referencedEntityId = null;
            if (value instanceof String) {
                try {
                    referencedEntityId = Long.valueOf((String) value);
                } catch (NumberFormatException e) {
                    validatedEntity.addError(fieldDefinition, "qcadooView.validate.field.error.wrongType", value.getClass()
                            .getSimpleName(), fieldDefinition.getType().getType().getSimpleName());
                }
            } else if (value instanceof Long) {
                referencedEntityId = (Long) value;
            } else if (value instanceof Integer) {
                referencedEntityId = Long.valueOf((Integer) value);
            } else if (value instanceof Entity) {
                referencedEntityId = ((Entity) value).getId();
            } else {
                validatedEntity.addError(fieldDefinition, "qcadooView.validate.field.error.wrongType", value.getClass()
                        .getSimpleName(), fieldDefinition.getType().getType().getSimpleName());
            }

            if (referencedEntityId != null) {
                BelongsToType belongsToFieldType = (BelongsToType) fieldDefinition.getType();
                referencedEntity = belongsToFieldType.getDataDefinition().get(referencedEntityId);
            }
        }
        return referencedEntity;
    }

    private void parseAndValidateEntity(final InternalDataDefinition dataDefinition, final Entity genericEntity) {
        for (Entry<String, FieldDefinition> fieldDefinitionEntry : dataDefinition.getFields().entrySet()) {
            Object validateFieldValue = parseAndValidateField((InternalFieldDefinition) fieldDefinitionEntry.getValue(),
                    genericEntity.getField(fieldDefinitionEntry.getKey()), genericEntity);
            genericEntity.setField(fieldDefinitionEntry.getKey(), validateFieldValue);
        }

        for (Entry<String, FieldDefinition> fieldDefinitionEntry : dataDefinition.getFields().entrySet()) {
            if (!genericEntity.isFieldValid(fieldDefinitionEntry.getKey())) {
                continue;
            }

            ((InternalFieldDefinition) fieldDefinitionEntry.getValue()).callValidators(genericEntity, null,
                    genericEntity.getField(fieldDefinitionEntry.getKey()));
        }

        if (genericEntity.isValid()) {
            dataDefinition.callValidators(genericEntity);
        }
    }

    private Object parseAndValidateValue(final InternalFieldDefinition fieldDefinition, final Object value,
            final Entity validatedEntity) {
        ValueAndError valueAndError = ValueAndError.empty();
        if (value != null) {
            valueAndError = fieldDefinition.getType().toObject(fieldDefinition, value);
            if (!valueAndError.isValid()) {
                validatedEntity.addError(fieldDefinition, valueAndError.getMessage(), valueAndError.getArgs());
                return null;
            }
        }

        if (fieldDefinition.callValidators(validatedEntity, null, valueAndError.getValue())) {
            return valueAndError.getValue();
        } else {
            return null;
        }
    }

    private Object parseAndValidateField(final InternalFieldDefinition fieldDefinition, final Object value,
            final Entity validatedEntity) {
        FieldType fieldType = fieldDefinition.getType();
        if (fieldType instanceof HasManyType || fieldType instanceof TreeType || fieldType instanceof ManyToManyType) {
            return value;
        } else {
            return parseAndValidateValue(fieldDefinition, trimAndNullIfEmpty(value), validatedEntity);
        }
    }

    private Object trimAndNullIfEmpty(final Object value) {
        if (value instanceof String && !StringUtils.hasText((String) value)) {
            return null;
        }
        if (value instanceof String) {
            return ((String) value).trim();
        }
        return value;
    }

}
