package com.qcadoo.model.api;

import java.math.MathContext;

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

}
