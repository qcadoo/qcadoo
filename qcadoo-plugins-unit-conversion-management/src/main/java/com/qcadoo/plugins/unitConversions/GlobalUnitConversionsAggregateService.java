package com.qcadoo.plugins.unitConversions;

/**
 * Service for managing unit conversions
 * 
 * @since 1.1.8
 * @author maku
 */
public interface GlobalUnitConversionsAggregateService {

    /**
     * Returns identifier of existing UnitConversionsAggregate entity. If entity does not exists creates new one.
     * 
     * @return identifier of existing or just created UnitConversionsAggregate entity
     * 
     * @throws IllegalStateException
     *             if created entity has a validation error
     */
    Long getAggregateId();
}
