package com.qcadoo.model.api.search;

import org.hibernate.criterion.Criterion;

/**
 * @since 0.4.1
 */
public interface SearchCriterion {

    /**
     * Internal use only.
     */
    Criterion getHibernateCriterion();

}
