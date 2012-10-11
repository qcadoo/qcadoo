package com.qcadoo.model.api.units;

import java.math.BigDecimal;

/**
 * Unit conversion
 * 
 * @since 1.1.8
 * @author maku
 */
public interface UnitConversion {

    /**
     * @return new reversed UnitConversion
     */
    UnitConversion reverse();

    /**
     * @param other
     * @return new UnitConversion object which represent conversion from this source unit to target unit of other UnitConversion
     *         given in argument
     */
    UnitConversion merge(final UnitConversion other);

    /**
     * @return conversion ratio
     */
    BigDecimal getRatio();

    /**
     * @return source unit symbol
     */
    String getUnitFrom();

    /**
     * @return target unit symbol
     */
    String getUnitTo();

}
