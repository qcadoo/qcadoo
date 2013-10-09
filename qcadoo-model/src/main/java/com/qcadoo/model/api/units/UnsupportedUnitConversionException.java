package com.qcadoo.model.api.units;

public class UnsupportedUnitConversionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnsupportedUnitConversionException(final String fromUnit, final String toUnit) {
        super(String.format("Conversion ratio between '%s' and '%s' is not defined", fromUnit, toUnit));
    }

}
