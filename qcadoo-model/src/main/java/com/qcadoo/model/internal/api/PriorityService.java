package com.qcadoo.model.internal.api;


public interface PriorityService {

    void prioritizeEntity(InternalDataDefinition dataDefinition, Object databaseEntity);

    void deprioritizeEntity(InternalDataDefinition dataDefinition, Object databaseEntity);

    void move(InternalDataDefinition dataDefinition, Object databaseEntity, int position, int offset);

}