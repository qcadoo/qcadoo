package com.qcadoo.model.api;

import java.math.MathContext;
import java.text.DecimalFormat;

/**
 * Service to provide utilities for numbers
 * 
 * @version 1.1.3
 */
public interface NumberService {

    /**
     * Provide global MathContext
     * 
     * @return {@link MathContext}
     */
    MathContext getMathContext();

    /**
     * Provide global DecimalFormat
     * 
     * @return {@link DecimalFormat}
     */
    DecimalFormat getDecimalFormat();

}
