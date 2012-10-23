package com.qcadoo.model.api.units;

import com.qcadoo.model.api.search.CustomRestriction;

/**
 * Unit conversion service
 * 
 * @since 1.1.8
 * @author maku
 */
public interface UnitConversionService {

    /**
     * @param unit
     * @return possible conversions object for given unit, generated using only global conversions.
     */
    PossibleUnitConversions getPossibleConversions(final String unit);

    /**
     * @param unit
     * @param customRestriction
     * @return possible conversions object for given unit, generated using only conversions which match given additional
     *         customRestriction.
     */
    PossibleUnitConversions getPossibleConversions(final String unit, final CustomRestriction customRestriction);

}
