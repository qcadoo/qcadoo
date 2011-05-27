package com.qcadoo.model.api.search;

import org.hibernate.criterion.Projections;

import com.qcadoo.model.internal.search.SearchProjectionImpl;
import com.qcadoo.model.internal.search.SearchProjectonListImpl;

/**
 * Utility with factory methods for {@link SearchProjection}.
 * 
 * @since 0.4.1
 */
public final class SearchProjections {

    private SearchProjections() {
    }

    /**
     * Creates projection's list.
     * 
     * @return projection's list
     */
    public static SearchProjectionList list() {
        return new SearchProjectonListImpl();
    }

    /**
     * Creates projection which add given field to the "GROUP BY" clause.
     * 
     * @param field
     *            field
     * @return projection
     */
    public static SearchProjection groupField(final String field) {
        return new SearchProjectionImpl(Projections.groupProperty(field));
    }

    /**
     * Creates projection which add given field to the "GROUP BY" clause and its "sum" to the "SELECT" clause.
     * 
     * @param field
     *            field
     * @return projection
     */
    public static SearchProjection sum(final String field) {
        return new SearchProjectionImpl(Projections.sum(field));
    }

    /**
     * Creates projection which add given field to the "GROUP BY" clause and its "max" to the "SELECT" clause.
     * 
     * @param field
     *            field
     * @return projection
     */
    public static SearchProjection max(final String field) {
        return new SearchProjectionImpl(Projections.max(field));
    }

    /**
     * Creates projection which add given field to the "GROUP BY" clause and its "min" to the "SELECT" clause.
     * 
     * @param field
     *            field
     * @return projection
     */
    public static SearchProjection min(final String field) {
        return new SearchProjectionImpl(Projections.min(field));
    }

    /**
     * Creates projection which add given field to the "GROUP BY" clause and its "avg" to the "SELECT" clause.
     * 
     * @param field
     *            field
     * @return projection
     */
    public static SearchProjection avg(final String field) {
        return new SearchProjectionImpl(Projections.avg(field));
    }

    /**
     * Creates projection which add "id" field to the "SELECT" clause.
     * 
     * @return projection
     */
    public static SearchProjection id() {
        return new SearchProjectionImpl(Projections.id());
    }

    /**
     * Creates projection which add given field to the "SELECT" clause.
     * 
     * @param field
     *            field
     * @return projection
     */
    public static SearchProjection field(final String field) {
        return new SearchProjectionImpl(Projections.property(field));
    }

    /**
     * Creates projection which add given field to the "GROUP BY" clause and its "count" to the "SELECT" clause.
     * 
     * @param field
     *            field
     * @return projection
     */
    public static SearchProjection count(final String field) {
        return new SearchProjectionImpl(Projections.count(field));
    }

    /**
     * Creates projection which add given field to the "GROUP BY" clause and its "count with distinct" to the "SELECT" clause.
     * 
     * @param field
     *            field
     * @return projection
     */
    public static SearchProjection countDistinct(final String field) {
        return new SearchProjectionImpl(Projections.countDistinct(field));
    }

    /**
     * Wraps projection with distinct projection.
     * 
     * @param projection
     *            projection
     * @return distincted projection
     */
    public static SearchProjection distinct(final SearchProjection projection) {
        return new SearchProjectionImpl(Projections.distinct(projection.getHibernateProjection()));
    }

    /**
     * Creates projection which add "count(*)" to the "SELECT" clause.
     * 
     * @return projection
     */
    public static SearchProjection rowCount() {
        return new SearchProjectionImpl(Projections.rowCount());
    }

    /**
     * Wraps projection with aliased projection.
     * 
     * @param projection
     *            projection
     * @param alias
     *            alias
     * @return aliased projection
     */
    public static SearchProjection alias(final SearchProjection projection, final String alias) {
        return new SearchProjectionImpl(Projections.alias(projection.getHibernateProjection(), alias));
    }

}
