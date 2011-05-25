/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.1
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.model.internal.search;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.DetachedCriteria;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchCriterion;
import com.qcadoo.model.api.search.SearchOrder;
import com.qcadoo.model.api.search.SearchOrders;
import com.qcadoo.model.api.search.SearchProjection;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.model.api.search.SearchResult;
import com.qcadoo.model.internal.api.InternalDataDefinition;

public final class SearchCriteriaImpl implements SearchCriteriaBuilder, SearchCriteria {

    private static final int DEFAULT_MAX_RESULTS = Integer.MAX_VALUE;

    private final DataDefinition sourceDataDefinition;

    private final DetachedCriteria criteria;

    private int maxResults = DEFAULT_MAX_RESULTS;

    private int firstResult;

    private final List<SearchOrder> orders = new ArrayList<SearchOrder>();

    private boolean hasProjection;

    public SearchCriteriaImpl(final DataDefinition dataDefinition) {
        checkNotNull(dataDefinition);
        sourceDataDefinition = dataDefinition;
        criteria = DetachedCriteria.forEntityName(((InternalDataDefinition) dataDefinition).getFullyQualifiedClassName());
    }

    public SearchCriteriaImpl(final DataDefinition dataDefinition, final String alias) {
        checkNotNull(dataDefinition);
        sourceDataDefinition = dataDefinition;
        criteria = DetachedCriteria.forEntityName(((InternalDataDefinition) dataDefinition).getFullyQualifiedClassName(), alias);
    }

    private SearchCriteriaImpl(final DetachedCriteria subcriteria) {
        sourceDataDefinition = null;
        criteria = subcriteria;
    }

    @Override
    public DataDefinition getDataDefinition() {
        if (!hasProjection) {
            return sourceDataDefinition;
        } else {
            return null;
        }
    }

    @Override
    public DetachedCriteria getHibernateDetachedCriteria() {
        return criteria;
    }

    @Override
    public SearchResult list() {
        return ((InternalDataDefinition) sourceDataDefinition).find(this);
    }

    @Override
    public Entity uniqueResult() {
        SearchResult results = list();

        if (results.getEntities().isEmpty()) {
            return null;
        } else if (results.getEntities().size() == 1) {
            return results.getEntities().get(0);
        } else {
            throw new IllegalStateException("Too many results, expected one, found " + results.getEntities().size());
        }
    }

    @Override
    public Criteria createCriteria(final Session session) {
        Criteria executableCriteria = criteria.getExecutableCriteria(session);

        return executableCriteria;
    }

    @Override
    public void addFirstAndMaxResults(final Criteria criteria) {
        criteria.setMaxResults(maxResults).setFirstResult(firstResult);
    }

    @Override
    public void addOrders(final Criteria criteria) {
        if (orders.size() == 0) {
            criteria.addOrder(org.hibernate.criterion.Order.asc("id"));
        } else {
            for (SearchOrder order : orders) {
                criteria.addOrder(order.getHibernateOrder());
            }
        }
    }

    @Override
    public SearchCriteriaBuilder setMaxResults(final int maxResults) {
        this.maxResults = maxResults;
        return this;
    }

    @Override
    public SearchCriteriaBuilder setFirstResult(final int firstResult) {
        this.firstResult = firstResult;
        return this;
    }

    @Override
    public SearchCriteriaBuilder setProjection(final SearchProjection projection) {
        criteria.setProjection(projection.getHibernateProjection());
        hasProjection = true;
        return this;
    }

    @Override
    public SearchCriteriaBuilder add(final SearchCriterion criterion) {
        criteria.add(criterion.getHibernateCriterion());
        return this;
    }

    @Override
    public SearchCriteriaBuilder addOrder(final SearchOrder order) {
        orders.add(order);
        return this;
    }

    @Override
    public SearchCriteriaBuilder createAlias(final String associationPath, final String alias) {
        criteria.createAlias(associationPath, alias);
        return this;
    }

    @Override
    public SearchCriteriaBuilder createCriteria(final String associationPath, final String alias) {
        DetachedCriteria subcriteria = criteria.createCriteria(associationPath, alias);
        return new SearchCriteriaImpl(subcriteria);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return "SearchCriteria[criteria=" + criteria + ", maxResults=" + maxResults + ", firstResult=" + firstResult + "]";
    }

    // depreceted

    @Override
    public SearchCriteriaBuilder like(final String fieldName, final String value) {
        return add(SearchRestrictions.like(fieldName, value));
    }

    @Override
    public SearchCriteriaBuilder isEq(final String fieldName, final Object value) {
        if (isLikeRestriction(value)) {
            return add(SearchRestrictions.like(fieldName, (String) value));
        }

        return add(SearchRestrictions.eq(fieldName, value));
    }

    private boolean isLikeRestriction(final Object value) {
        return value instanceof String && ((String) value).matches(".*[\\*%\\?_].*");
    }

    @Override
    public SearchCriteriaBuilder isLe(final String fieldName, final Object value) {
        return add(SearchRestrictions.le(fieldName, value));
    }

    @Override
    public SearchCriteriaBuilder isLt(final String fieldName, final Object value) {
        return add(SearchRestrictions.lt(fieldName, value));
    }

    @Override
    public SearchCriteriaBuilder isGe(final String fieldName, final Object value) {
        return add(SearchRestrictions.ge(fieldName, value));
    }

    @Override
    public SearchCriteriaBuilder isGt(final String fieldName, final Object value) {
        return add(SearchRestrictions.gt(fieldName, value));
    }

    @Override
    public SearchCriteriaBuilder isNe(final String fieldName, final Object value) {
        if (isLikeRestriction(value)) {
            return add(SearchRestrictions.not(SearchRestrictions.like(fieldName, (String) value)));
        }

        return add(SearchRestrictions.ne(fieldName, value));
    }

    @Override
    public SearchCriteriaBuilder isNotNull(final String fieldName) {
        return add(SearchRestrictions.isNotNull(fieldName));
    }

    @Override
    public SearchCriteriaBuilder isNull(final String fieldName) {
        return add(SearchRestrictions.isNull(fieldName));
    }

    @Override
    public SearchCriteriaBuilder openNot() {
        // TODO restrictions.offerFirst(new ArrayList<Restriction>());
        return this;
    }

    @Override
    public SearchCriteriaBuilder closeNot() {
        // TODO List<Restriction> notRestrictions = restrictions.remove();
        //
        // if (restrictions.size() < 1) {
        // throw new IllegalStateException("Restrictions stack is empty : " + notRestrictions);
        // }
        //
        // if (notRestrictions.size() > 1) {
        // return addRestriction(new LogicalOperatorRestriction(RestrictionLogicalOperator.NOT, new LogicalOperatorRestriction(
        // RestrictionLogicalOperator.AND, notRestrictions.toArray(new Restriction[notRestrictions.size()]))));
        // } else if (notRestrictions.size() > 0) {
        // return addRestriction(new LogicalOperatorRestriction(RestrictionLogicalOperator.NOT,
        // notRestrictions.toArray(new Restriction[notRestrictions.size()])));
        // } else {
        // return this;
        // }
        return this;
    }

    @Override
    public SearchCriteriaBuilder openOr() {
        // TODO restrictions.offerFirst(new ArrayList<Restriction>());
        return this;
    }

    @Override
    public SearchCriteriaBuilder closeOr() {
        // List<Restriction> orRestrictions = restrictions.remove();
        //
        // if (restrictions.size() < 1) {
        // throw new IllegalStateException("Restrictions stack is empty : " + orRestrictions);
        // }
        //
        // if (orRestrictions.size() > 0) {
        // return addRestriction(new LogicalOperatorRestriction(RestrictionLogicalOperator.OR,
        // orRestrictions.toArray(new Restriction[orRestrictions.size()])));
        // } else {
        // return this;
        // }
        return this;
    }

    @Override
    public SearchCriteriaBuilder openAnd() {
        // TODO restrictions.offerFirst(new ArrayList<Restriction>());
        return this;
    }

    @Override
    public SearchCriteriaBuilder closeAnd() {
        // TODO List<Restriction> andRestrictions = restrictions.remove();
        //
        // if (restrictions.size() < 1) {
        // throw new IllegalStateException("Restrictions stack is empty : " + andRestrictions);
        // }
        //
        // if (andRestrictions.size() > 0) {
        // return addRestriction(new LogicalOperatorRestriction(RestrictionLogicalOperator.AND,
        // andRestrictions.toArray(new Restriction[andRestrictions.size()])));
        // } else {
        // return this;
        // }
        return this;
    }

    @Override
    public SearchCriteriaBuilder belongsTo(final String fieldName, final Object entityOrId) {
        if (entityOrId instanceof Entity) {
            return add(SearchRestrictions.belongsTo(fieldName, (Entity) entityOrId));
        } else {
            // TODO
            return this;
        }
    }

    @Override
    public SearchCriteriaBuilder isIdEq(final Long id) {
        return add(SearchRestrictions.eq("id", id));
    }

    @Override
    public SearchCriteriaBuilder isIdLe(final Long id) {
        return add(SearchRestrictions.le("id", id));
    }

    @Override
    public SearchCriteriaBuilder isIdLt(final Long id) {
        return add(SearchRestrictions.lt("id", id));
    }

    @Override
    public SearchCriteriaBuilder isIdGe(final Long id) {
        return add(SearchRestrictions.ge("id", id));
    }

    @Override
    public SearchCriteriaBuilder isIdGt(final Long id) {
        return add(SearchRestrictions.gt("id", id));
    }

    @Override
    public SearchCriteriaBuilder isIdNe(final Long id) {
        return add(SearchRestrictions.ne("id", id));
    }

    @Override
    public SearchCriteriaBuilder orderAscBy(final String fieldName) {
        return addOrder(SearchOrders.asc(fieldName));
    }

    @Override
    public SearchCriteriaBuilder orderDescBy(final String fieldName) {
        return addOrder(SearchOrders.desc(fieldName));
    }

}
