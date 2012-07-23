package com.qcadoo.view.internal.api;

public enum EnabledAttribute {
    TRUE("true"), FALSE("false"), NEVER("never");

    private final String stringValue;

    private EnabledAttribute(final String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public static EnabledAttribute parseString(final String string) {
        for (EnabledAttribute enumValue : EnabledAttribute.values()) {
            if (enumValue.getStringValue().equalsIgnoreCase(string)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("Couldn't parse enum from string '" + string + "'");
    }
}
