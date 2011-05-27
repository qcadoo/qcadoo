package com.qcadoo.model.api.search;

import org.hibernate.criterion.Order;

/**
 * A order used in {@link SearchCriteriaBuilder#addOrder(SearchOrder)}. It represents the SQL's "ORDER BY" clause.
 * 
 * @since 0.4.1
 */
public interface SearchOrder {

    /**
     * Returns Hibernate's representation of this order.<br/>
     * <br/>
     * <b>This method is not the part of the public API, it may be modified in the future.</b>
     */
    Order getHibernateOrder();

}
