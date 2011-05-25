package com.qcadoo.model.api.search;

import org.hibernate.criterion.Subqueries;

/**
 * @since 0.4.1
 */
public class SearchSubqueries {

    public static SearchCriterion notExists(final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.notExists(criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion exists(final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.exists(criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion in(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.in(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldIn(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyNotIn(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion notIn(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.notIn(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldNotIn(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyNotIn(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion eq(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.eq(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion eqAll(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.eqAll(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldEq(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyEq(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldEqAll(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyEqAll(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion ge(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.ge(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion geAll(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.geAll(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion geSome(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.geSome(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldGe(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyGe(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldGeAll(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyGeAll(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldGeSome(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyGeSome(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion gt(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.gt(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion gtAll(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.gtAll(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion gtSome(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.gtSome(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldGt(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyGt(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldGtAll(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyGtAll(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldGtSome(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyGtSome(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion lt(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.lt(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion ltAll(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.ltAll(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion ltSome(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.ltSome(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldLt(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyLt(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldLtAll(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyLtAll(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldLtSome(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyLtSome(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion le(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.le(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion leAll(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.leAll(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion leSome(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.leSome(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldLe(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyLe(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldLeAll(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyLeAll(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldLeSome(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyLeSome(field, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion ne(final Object value, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.ne(value, criteria.getHibernateDetachedCriteria()));
    }

    public static SearchCriterion fieldNe(final String field, final SearchCriteriaBuilder criteria) {
        return new SearchCriterionImpl(Subqueries.propertyNe(field, criteria.getHibernateDetachedCriteria()));
    }

}
