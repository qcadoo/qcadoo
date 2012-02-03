package com.qcadoo.model.api;

import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Locale;

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
     * @param locale
     *            locale
     * 
     * @return {@link DecimalFormat}
     */
    DecimalFormat getDecimalFormat(Locale locale);

}
