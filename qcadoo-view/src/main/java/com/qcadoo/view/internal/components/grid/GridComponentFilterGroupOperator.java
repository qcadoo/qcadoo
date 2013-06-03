package com.qcadoo.view.internal.components.grid;

public enum GridComponentFilterGroupOperator {

    AND("AND"), OR("OR");

    private String value;

    private GridComponentFilterGroupOperator(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
