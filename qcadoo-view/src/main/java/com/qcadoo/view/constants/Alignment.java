package com.qcadoo.view.constants;

import org.apache.commons.lang3.StringUtils;

public enum Alignment {

    LEFT("left"), RIGHT("right");

    private final String stringValue;

    private Alignment(final String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public static Alignment parseString(final String stringValue) {
        for (Alignment value : Alignment.values()) {
            if (StringUtils.equalsIgnoreCase(value.getStringValue(), stringValue)) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("Can't parse Alignment from string '%s'", stringValue));
    }

}
