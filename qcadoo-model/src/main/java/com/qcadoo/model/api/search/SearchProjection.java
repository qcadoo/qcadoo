package com.qcadoo.model.api.search;

import org.hibernate.criterion.Projection;

/**
 * @since 0.4.1
 */
public interface SearchProjection {

    /**
     * Internal use only.
     */
    Projection getHibernateProjection();

}
