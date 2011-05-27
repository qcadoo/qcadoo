package com.qcadoo.model.api.search;

import org.hibernate.criterion.Order;

import com.qcadoo.model.internal.search.SearchOrderImpl;

/**
 * Utility with factory methods for {@link SearchOrder}.
 * 
 * @since 0.4.1
 */
public final class SearchOrders {

    private SearchOrders() {
    }

    /**
     * Creates ascending order using given field.
     * 
     * @param field
     *            field
     * @return order
     */
    public static SearchOrder asc(final String field) {
        return new SearchOrderImpl(Order.asc(field));
    }

    /**
     * Creates descending order using given field.
     * 
     * @param field
     *            field
     * @return order
     */
    public static SearchOrder desc(final String field) {
        return new SearchOrderImpl(Order.desc(field));
    }

}
