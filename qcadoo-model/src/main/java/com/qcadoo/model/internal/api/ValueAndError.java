package com.qcadoo.model.internal.api;

public final class ValueAndError {

    private final Object value;

    private final String message;

    private final String[] args;

    private ValueAndError(final Object value, final String message, final String... args) {
        this.value = value;
        this.message = message;
        this.args = args;
    }

    public static ValueAndError withoutError(final Object value) {
        return new ValueAndError(value, null);
    }

    public static ValueAndError empty() {
        return new ValueAndError(null, null);
    }

    public static ValueAndError withError(final String message, final String... args) {
        return new ValueAndError(null, message, args);
    }

    public boolean isValid() {
        return message == null;
    }

    public Object getValue() {
        return value;
    }

    public String[] getArgs() {
        return args;
    }

    public String getMessage() {
        return message;
    }

}
