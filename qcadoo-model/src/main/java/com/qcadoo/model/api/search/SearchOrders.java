package com.qcadoo.model.api.search;

import org.hibernate.criterion.Order;

import com.qcadoo.model.internal.search.SearchOrderImpl;

/**
 * @since 0.4.1
 */
public class SearchOrders {

    public static SearchOrder asc(final String field) {
        return new SearchOrderImpl(Order.asc(field));
    }

    public static SearchOrder desc(final String field) {
        return new SearchOrderImpl(Order.desc(field));
    }

}
