package com.qcadoo.model.internal.api;

import com.qcadoo.model.api.Entity;

public interface ValidationService {

    void validateGenericEntity(InternalDataDefinition dataDefinition, Entity genericEntity, Entity existingGenericEntity);

}