package com.qcadoo.model.api.search;

import org.hibernate.criterion.Projection;

/**
 * A projection used in {@link SearchCriteriaBuilder#setProjection(SearchProjection)}. It represents the SQL's "SELECT" clause.
 * 
 * @since 0.4.1
 */
public interface SearchProjection {

    /**
     * Returns Hibernate's representation of this projection.<br/>
     * <br/>
     * <b>This method is not the part of the public API, it may be modified in the future.</b>
     */
    Projection getHibernateProjection();

}
