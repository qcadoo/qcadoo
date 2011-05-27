package com.qcadoo.model.api.search;

import org.hibernate.criterion.Criterion;

/**
 * A restriction used in {@link SearchCriteriaBuilder#add(SearchCriterion)}. It represents the SQL's WHERE clause.
 * 
 * @since 0.4.1
 */
public interface SearchCriterion {

    /**
     * Returns Hibernate's representation of this criterion.<br/>
     * <br/>
     * <b>This method is not the part of the public API, it may be modified in the future.</b>
     */
    Criterion getHibernateCriterion();

}
