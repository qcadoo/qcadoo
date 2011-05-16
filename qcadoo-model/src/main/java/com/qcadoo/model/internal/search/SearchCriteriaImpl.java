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
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchResult;
import com.qcadoo.model.api.types.BelongsToType;
import com.qcadoo.model.internal.api.InternalDataDefinition;
import com.qcadoo.model.internal.api.ValueAndError;
import com.qcadoo.model.internal.search.restrictions.BelongsToRestriction;
import com.qcadoo.model.internal.search.restrictions.IsNotNullRestriction;
import com.qcadoo.model.internal.search.restrictions.IsNullRestriction;
import com.qcadoo.model.internal.search.restrictions.LikeRestriction;
import com.qcadoo.model.internal.search.restrictions.LogicalOperatorRestriction;
import com.qcadoo.model.internal.search.restrictions.SimpleRestriction;

public final class SearchCriteriaImpl implements SearchCriteriaBuilder, SearchCriteria {

    private static final int DEFAULT_MAX_RESULTS = Integer.MAX_VALUE;

    private int maxResults = DEFAULT_MAX_RESULTS;

    private int firstResult = 0;

    private Order order;

    private final Deque<List<Restriction>> restrictions = new LinkedList<List<Restriction>>();

    private final DataDefinition dataDefinition;

    public SearchCriteriaImpl(final DataDefinition dataDefinition) {
        checkNotNull(dataDefinition);
        this.dataDefinition = dataDefinition;
        if (dataDefinition.getPriorityField() != null) {
            order = Order.asc(dataDefinition.getPriorityField().getName());
        } else {
            order = Order.asc();
        }
        restrictions.offerFirst(new ArrayList<Restriction>());
    }

    @Override
    public DataDefinition getDataDefinition() {
        return dataDefinition;
    }

    @Override
    public int getMaxResults() {
        return maxResults;
    }

    @Override
    public int getFirstResult() {
        return firstResult;
    }

    @Override
    public Order getOrder() {
        return order;
    }

    @Override
    public List<Restriction> getRestrictions() {
        if (restrictions.size() == 1) {
            return restrictions.element();
        } else {
            throw new IllegalStateException("Restrictions stack must have exactly one element, found " + restrictions.size());
        }
    }

    @Override
    public SearchResult list() {
        return ((InternalDataDefinition) dataDefinition).find(this);
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

    private SearchCriteriaBuilder addRestriction(final Restriction restriction) {
        this.restrictions.element().add(restriction);
        return this;
    }

    @Override
    public SearchCriteriaBuilder orderAscBy(final String fieldName) {
        checkNotNull(fieldName);
        this.order = Order.asc(fieldName);
        return this;
    }

    @Override
    public SearchCriteriaBuilder orderDescBy(final String fieldName) {
        checkNotNull(fieldName);
        this.order = Order.desc(fieldName);
        return this;
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
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return "SearchCriteria[dataDefinition=" + dataDefinition.getPluginIdentifier() + "." + dataDefinition.getName()
                + ", maxResults=" + maxResults + ", firstResult=" + firstResult + ", order=" + order + ", restrictions="
                + restrictions + "]";
    }

    @Override
    public SearchCriteriaBuilder like(final String fieldName, final String value) {
        return addRestriction(new LikeRestriction(fieldName, value.replace('*', '%').replace('?', '_')));
    }

    @Override
    public SearchCriteriaBuilder isEq(final String fieldName, final Object value) {
        if (isLikeRestriction(value)) {
            return like(fieldName, (String) value);
        }

        ValueAndError valueAndError = validateValue(getFieldDefinition(fieldName), value);

        if (!valueAndError.isValid()) {
            return this;
        }

        return addRestriction(new SimpleRestriction(fieldName, valueAndError.getValue(), RestrictionOperator.EQ));
    }

    private boolean isLikeRestriction(final Object value) {
        return value instanceof String && ((String) value).matches(".*[\\*%\\?_].*");
    }

    @Override
    public SearchCriteriaBuilder isLe(final String fieldName, final Object value) {
        ValueAndError valueAndError = validateValue(getFieldDefinition(fieldName), value);

        if (!valueAndError.isValid()) {
            return this;
        }

        return addRestriction(new SimpleRestriction(fieldName, valueAndError.getValue(), RestrictionOperator.LE));
    }

    @Override
    public SearchCriteriaBuilder isLt(final String fieldName, final Object value) {
        ValueAndError valueAndError = validateValue(getFieldDefinition(fieldName), value);

        if (!valueAndError.isValid()) {
            return this;
        }

        return addRestriction(new SimpleRestriction(fieldName, valueAndError.getValue(), RestrictionOperator.LT));
    }

    @Override
    public SearchCriteriaBuilder isGe(final String fieldName, final Object value) {
        ValueAndError valueAndError = validateValue(getFieldDefinition(fieldName), value);

        if (!valueAndError.isValid()) {
            return this;
        }

        return addRestriction(new SimpleRestriction(fieldName, valueAndError.getValue(), RestrictionOperator.GE));
    }

    @Override
    public SearchCriteriaBuilder isGt(final String fieldName, final Object value) {
        ValueAndError valueAndError = validateValue(getFieldDefinition(fieldName), value);

        if (!valueAndError.isValid()) {
            return this;
        }

        return addRestriction(new SimpleRestriction(fieldName, valueAndError.getValue(), RestrictionOperator.GT));
    }

    @Override
    public SearchCriteriaBuilder isNe(final String fieldName, final Object value) {
        if (isLikeRestriction(value)) {
            return openNot().like(fieldName, (String) value).closeNot();
        }

        ValueAndError valueAndError = validateValue(getFieldDefinition(fieldName), value);

        if (!valueAndError.isValid()) {
            return this;
        }

        return addRestriction(new SimpleRestriction(fieldName, valueAndError.getValue(), RestrictionOperator.NE));
    }

    @Override
    public SearchCriteriaBuilder isNotNull(final String fieldName) {
        return addRestriction(new IsNotNullRestriction(fieldName));
    }

    @Override
    public SearchCriteriaBuilder isNull(final String fieldName) {
        return addRestriction(new IsNullRestriction(fieldName));
    }

    @Override
    public SearchCriteriaBuilder openNot() {
        restrictions.offerFirst(new ArrayList<Restriction>());
        return this;
    }

    @Override
    public SearchCriteriaBuilder closeNot() {
        List<Restriction> notRestrictions = restrictions.remove();

        if (restrictions.size() < 1) {
            throw new IllegalStateException("Restrictions stack is empty : " + notRestrictions);
        }

        if (notRestrictions.size() > 1) {
            return addRestriction(new LogicalOperatorRestriction(RestrictionLogicalOperator.NOT, new LogicalOperatorRestriction(
                    RestrictionLogicalOperator.AND, notRestrictions.toArray(new Restriction[notRestrictions.size()]))));
        } else if (notRestrictions.size() > 0) {
            return addRestriction(new LogicalOperatorRestriction(RestrictionLogicalOperator.NOT,
                    notRestrictions.toArray(new Restriction[notRestrictions.size()])));
        } else {
            return this;
        }
    }

    @Override
    public SearchCriteriaBuilder openOr() {
        restrictions.offerFirst(new ArrayList<Restriction>());
        return this;
    }

    @Override
    public SearchCriteriaBuilder closeOr() {
        List<Restriction> orRestrictions = restrictions.remove();

        if (restrictions.size() < 1) {
            throw new IllegalStateException("Restrictions stack is empty : " + orRestrictions);
        }

        if (orRestrictions.size() > 0) {
            return addRestriction(new LogicalOperatorRestriction(RestrictionLogicalOperator.OR,
                    orRestrictions.toArray(new Restriction[orRestrictions.size()])));
        } else {
            return this;
        }
    }

    @Override
    public SearchCriteriaBuilder openAnd() {
        restrictions.offerFirst(new ArrayList<Restriction>());
        return this;
    }

    @Override
    public SearchCriteriaBuilder closeAnd() {
        List<Restriction> andRestrictions = restrictions.remove();

        if (restrictions.size() < 1) {
            throw new IllegalStateException("Restrictions stack is empty : " + andRestrictions);
        }

        if (andRestrictions.size() > 0) {
            return addRestriction(new LogicalOperatorRestriction(RestrictionLogicalOperator.AND,
                    andRestrictions.toArray(new Restriction[andRestrictions.size()])));
        } else {
            return this;
        }
    }

    @Override
    public SearchCriteriaBuilder belongsTo(final String fieldName, final Object entityOrId) {
        if (entityOrId == null) {
            return addRestriction(new IsNullRestriction(fieldName));
        }

        if (entityOrId instanceof Long) {
            return addRestriction(new BelongsToRestriction(fieldName, (Long) entityOrId));
        } else {
            try {
                return addRestriction(new BelongsToRestriction(fieldName, (Long) PropertyUtils.getProperty(entityOrId, "id")));
            } catch (Exception e) {
                throw new IllegalStateException("cannot get value of the property: " + entityOrId.getClass().getSimpleName()
                        + ", id", e);
            }
        }
    }

    @Override
    public SearchCriteriaBuilder isIdEq(final Long id) {
        return addRestriction(new SimpleRestriction("id", id, RestrictionOperator.EQ));
    }

    @Override
    public SearchCriteriaBuilder isIdLe(final Long id) {
        return addRestriction(new SimpleRestriction("id", id, RestrictionOperator.LE));
    }

    @Override
    public SearchCriteriaBuilder isIdLt(final Long id) {
        return addRestriction(new SimpleRestriction("id", id, RestrictionOperator.LT));
    }

    @Override
    public SearchCriteriaBuilder isIdGe(final Long id) {
        return addRestriction(new SimpleRestriction("id", id, RestrictionOperator.GE));
    }

    @Override
    public SearchCriteriaBuilder isIdGt(final Long id) {
        return addRestriction(new SimpleRestriction("id", id, RestrictionOperator.GT));
    }

    @Override
    public SearchCriteriaBuilder isIdNe(final Long id) {
        return addRestriction(new SimpleRestriction("id", id, RestrictionOperator.NE));
    }

    private FieldDefinition getFieldDefinition(final String field) {
        String[] path = field.split("\\.");

        DataDefinition tmpDataDefinition = dataDefinition;

        for (int i = 0; i < path.length; i++) {

            if (tmpDataDefinition.getField(path[i]) == null) {
                throw new IllegalStateException("Cannot resolve field " + field + " of " + dataDefinition);
            }

            FieldDefinition fieldDefinition = tmpDataDefinition.getField(path[i]);

            if (i < path.length - 1) {
                if (fieldDefinition.getType() instanceof BelongsToType) {
                    tmpDataDefinition = ((BelongsToType) fieldDefinition.getType()).getDataDefinition();
                    continue;
                } else {
                    throw new IllegalStateException("Cannot resolve field " + field + " of " + dataDefinition);
                }
            }

            return fieldDefinition;
        }

        throw new IllegalStateException("Cannot resolve field " + field + " of " + dataDefinition);
    }

    private ValueAndError validateValue(final FieldDefinition fieldDefinition, final Object value) {
        Object fieldValue = value;
        if (fieldValue != null && !fieldDefinition.getType().getType().isInstance(fieldValue)) {
            if (fieldValue instanceof String) {
                return fieldDefinition.getType().toObject(fieldDefinition, fieldValue);
            } else {
                return ValueAndError.empty();
            }
        }
        return ValueAndError.withoutError(fieldValue);
    }
}
