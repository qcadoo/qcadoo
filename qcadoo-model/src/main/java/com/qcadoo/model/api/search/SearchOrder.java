package com.qcadoo.model.api.search;

import org.hibernate.criterion.Order;

/**
 * @since 0.4.1
 */
public interface SearchOrder {

    /**
     * Internal use only.
     */
    Order getHibernateOrder();

}
