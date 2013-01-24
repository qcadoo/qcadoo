package com.qcadoo.view.internal.components.grid;

/**
 * @author marcinkubala
 * @since 1.2.0
 */
public class GridComponentFilterException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String filterValue;

    /**
     * @param filterValue
     *            value of column's filter
     */
    public GridComponentFilterException(final String filterValue) {
        super();
        this.filterValue = filterValue;
    }

    /**
     * Returns value of column's filter
     * 
     * @return value of column's filter
     */
    public String getFilterValue() {
        return filterValue;
    }

}
