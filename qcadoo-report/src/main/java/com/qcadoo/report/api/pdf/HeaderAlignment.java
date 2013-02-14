package com.qcadoo.report.api.pdf;

public enum HeaderAlignment {
    LEFT("01left"), RIGHT("02right"), CENTER("03center");

    private String alignment;

    private HeaderAlignment(final String alignment) {
        this.alignment = alignment;
    }

    public String getStringValue() {
        return alignment;
    }

    public static HeaderAlignment parseString(final String alignment) {
        if ("01left".equals(alignment)) {
            return LEFT;
        } else if ("02right".equals(alignment)) {
            return RIGHT;
        } else if ("03center".equals(alignment)) {
            return CENTER;
        }

        throw new IllegalStateException("Unsupported header alignment '" + alignment + "'");
    }
}
