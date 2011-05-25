package com.qcadoo.model.internal.search;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.qcadoo.model.api.search.SearchProjection;
import com.qcadoo.model.api.search.SearchProjectionList;

public class SearchProjectonListImpl implements SearchProjectionList {

    private final ProjectionList projections;

    public SearchProjectonListImpl() {
        projections = Projections.projectionList();
    }

    @Override
    public Projection getHibernateProjection() {
        return projections;
    }

    @Override
    public SearchProjectionList add(final SearchProjection projection) {
        projections.add(projection.getHibernateProjection());
        return this;
    }

}
