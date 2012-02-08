package com.qcadoo.model.api;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Service to provide utilities for numbers
 * 
 * @version 1.1.3
 */
public interface NumberService {

    /**
     * Provide global MathContext.
     * 
     * @return {@link MathContext}
     */
    MathContext getMathContext();

    /**
     * Formats an object with DecimalFormat to produce a String.
     * 
     * @param obj
     * 
     * @return Formatted string.
     */
    String format(Object obj);

    /**
     * Set scale 3 to {@link BigDecimal} with RoundingMode HALF_EVEN.
     * 
     * @param decimal
     * 
     * @return BigDecimal with scale 3.
     */
    BigDecimal setScale(BigDecimal decimal);

}
