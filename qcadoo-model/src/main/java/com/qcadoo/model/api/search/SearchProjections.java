package com.qcadoo.model.api.search;

import org.hibernate.criterion.Projections;

import com.qcadoo.model.internal.search.SearchProjectionImpl;
import com.qcadoo.model.internal.search.SearchProjectonListImpl;

/**
 * @since 0.4.1
 */
public class SearchProjections {

    public static SearchProjectionList projectionList() {
        return new SearchProjectonListImpl();
    }

    public static SearchProjection groupProperty(final String field) {
        return new SearchProjectionImpl(Projections.groupProperty(field));
    }

    public static SearchProjection sum(final String field) {
        return new SearchProjectionImpl(Projections.sum(field));
    }

    public static SearchProjection max(final String field) {
        return new SearchProjectionImpl(Projections.max(field));
    }

    public static SearchProjection min(final String field) {
        return new SearchProjectionImpl(Projections.min(field));
    }

    public static SearchProjection avg(final String field) {
        return new SearchProjectionImpl(Projections.avg(field));
    }

    public static SearchProjection id() {
        return new SearchProjectionImpl(Projections.id());
    }

    public static SearchProjection property(final String field) {
        return new SearchProjectionImpl(Projections.property(field));
    }

    public static SearchProjection count(final String field) {
        return new SearchProjectionImpl(Projections.count(field));
    }

    public static SearchProjection countDistinct(final String field) {
        return new SearchProjectionImpl(Projections.countDistinct(field));
    }

    public static SearchProjection distinct(final SearchProjection projection) {
        return new SearchProjectionImpl(Projections.distinct(projection.getHibernateProjection()));
    }

    public static SearchProjection rowCount() {
        return new SearchProjectionImpl(Projections.rowCount());
    }

    public static SearchProjection alias(final SearchProjection projection, final String alias) {
        return new SearchProjectionImpl(Projections.alias(projection.getHibernateProjection(), alias));
    }

}
