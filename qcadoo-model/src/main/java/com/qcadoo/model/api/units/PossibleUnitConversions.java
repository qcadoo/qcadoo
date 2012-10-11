package com.qcadoo.model.api.units;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qcadoo.model.api.Entity;

/**
 * Object representing possible unit conversions
 * 
 * @since 1.1.8
 * @author maku
 */
public interface PossibleUnitConversions {

    /**
     * @return source unit
     */
    String getUnitFrom();

    /**
     * @param value
     * @param unitTo
     * @return value in specified unit
     * @throws NullPointerException
     *             if conversion is not defined
     */
    BigDecimal convertTo(final BigDecimal value, final String unitTo);

    /**
     * @return true if there is no available conversions matching source unit (source unit can be obtained using
     *         {@link #getUnitFrom()}).
     */
    boolean isEmpty();

    /**
     * @return Set of supported target units
     */
    Set<String> getSupportedUnits();

    /**
     * @param unit
     * @return true if conversion to specified unit is defined
     */
    boolean isDefinedFor(final String unit);

    /**
     * @return all matching conversions as map containing target unit -> conversion ratio pairs.
     */
    Map<String, BigDecimal> asUnitToConversionMap();

    /**
     * Get all matching conversions as list of UnitConversionItem entities, belonging to specified owner Entity.
     * 
     * @param ownerFieldName
     *            owner entity field name
     * @param ownerEntity
     *            owner entity
     * @return all matching conversions as list of UnitConversionItem entities
     */
    List<Entity> asEntities(final String ownerFieldName, final Entity ownerEntity);

}
