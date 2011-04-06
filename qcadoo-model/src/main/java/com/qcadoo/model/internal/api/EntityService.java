package com.qcadoo.model.internal.api;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;

public interface EntityService {

    String FIELD_ID = "id";

    Long getId(Object databaseEntity);

    void setId(Object databaseEntity, Long id);

    void setField(Object databaseEntity, FieldDefinition fieldDefinition, Object value);

    Object getField(Object databaseEntity, FieldDefinition fieldDefinition);

    Entity convertToGenericEntity(InternalDataDefinition dataDefinition, Object databaseEntity);

    Object convertToDatabaseEntity(InternalDataDefinition dataDefinition, Entity genericEntity, Object existingDatabaseEntity);

}