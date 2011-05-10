/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.0
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

package com.qcadoo.model.api.search;

import org.apache.commons.beanutils.PropertyUtils;

import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.internal.api.ValueAndError;
import com.qcadoo.model.internal.search.Restriction;
import com.qcadoo.model.internal.search.RestrictionLogicalOperator;
import com.qcadoo.model.internal.search.RestrictionOperator;
import com.qcadoo.model.internal.search.restrictions.BelongsToRestriction;
import com.qcadoo.model.internal.search.restrictions.IsNotNullRestriction;
import com.qcadoo.model.internal.search.restrictions.IsNullRestriction;
import com.qcadoo.model.internal.search.restrictions.LikeRestriction;
import com.qcadoo.model.internal.search.restrictions.LogicalOperatorRestriction;
import com.qcadoo.model.internal.search.restrictions.SimpleRestriction;

/**
 * Class creates restrictions for search criteria.
 * 
 * @since 0.4.0
 * @deprecated
 */
@Deprecated
public final class Restrictions {

    private Restrictions() {
    }

    /**
     * Create "equals" restriction for given field's definition. If expected value contains "%", "*", "?" or "_" sql "like"
     * restriction will be used.
     * 
     * @param fieldDefinition
     *            field's definition
     * @param expectedValue
     *            expected value
     * @return restriction
     */
    public static Restriction eq(final FieldDefinition fieldDefinition, final Object expectedValue) {
        ValueAndError value = validateValue(fieldDefinition, expectedValue);
        if (!value.isValid()) {
            return null;
        }
        return createEqRestriction(fieldDefinition.getName(), value.getValue());
    }

    /**
     * Create "equals" restriction for given field's name. If expected value contains "%", "*", "?" or "_" sql "like" restriction
     * will be used.
     * 
     * @param fieldName
     *            field's name
     * @param expectedValue
     *            expected value
     * @return restriction
     * @deprecated
     */
    @Deprecated
    public static Restriction eq(final String fieldName, final Object expectedValue) {
        return createEqRestriction(fieldName, expectedValue);
    }

    private static Restriction createEqRestriction(final String fieldName, final Object expectedValue) {
        if (expectedValue instanceof String && ((String) expectedValue).matches(".*[\\*%\\?_].*")) {
            String preperadValue = ((String) expectedValue).replace('*', '%').replace('?', '_');
            return new LikeRestriction(fieldName, preperadValue);
        }
        return new SimpleRestriction(fieldName, expectedValue, RestrictionOperator.EQ);
    }

    /**
     * Create "belongsTo" restriction for given field's definition. Expected value can be both entity object or its id.
     * 
     * @param fieldDefinition
     *            field's definition
     * @param entityOrId
     *            entity or id
     * @return restriction
     */
    public static Restriction belongsTo(final FieldDefinition fieldDefinition, final Object entityOrId) {
        if (entityOrId instanceof Long) {
            return new BelongsToRestriction(fieldDefinition.getName(), (Long) entityOrId);
        } else {
            try {
                return new BelongsToRestriction(fieldDefinition.getName(), (Long) PropertyUtils.getProperty(entityOrId, "id"));
            } catch (Exception e) {
                throw new IllegalStateException("cannot get value of the property: " + entityOrId.getClass().getSimpleName()
                        + ", id", e);
            }
        }
    }

    /**
     * Create simple restriction for id field, comparing it to expected value using operator "equal".
     * 
     * @param id
     *            expected value
     * @return restriction
     */
    public static Restriction idEq(final Long id) {
        return new SimpleRestriction("id", id, RestrictionOperator.EQ);
    }

    /**
     * Create simple restriction for id field, comparing it to expected value using operator "less than or equal".
     * 
     * @param id
     *            expected value
     * @return restriction
     */
    public static Restriction idLe(final Long id) {
        return new SimpleRestriction("id", id, RestrictionOperator.LE);
    }

    /**
     * Create simple restriction for id field, comparing it to expected value using operator "less than".
     * 
     * @param id
     *            expected value
     * @return restriction
     */
    public static Restriction idLt(final Long id) {
        return new SimpleRestriction("id", id, RestrictionOperator.LT);
    }

    /**
     * Create simple restriction for id field, comparing it to expected value using operator "greater than or equal".
     * 
     * @param id
     *            expected value
     * @return restriction
     */
    public static Restriction idGe(final Long id) {
        return new SimpleRestriction("id", id, RestrictionOperator.GE);
    }

    /**
     * Create simple restriction for id field, comparing it to expected value using operator "greater than".
     * 
     * @param id
     *            expected value
     * @return restriction
     */
    public static Restriction idGt(final Long id) {
        return new SimpleRestriction("id", id, RestrictionOperator.GT);
    }

    /**
     * Create simple restriction for id field, comparing it to expected value using operator "not equal".
     * 
     * @param id
     *            expected value
     * @return restriction
     */
    public static Restriction idNe(final Long id) {
        return new SimpleRestriction("id", id, RestrictionOperator.NE);
    }

    /**
     * Create "greater or equals" restriction for given field's definition.
     * 
     * @param fieldDefinition
     *            field's definition
     * @param expectedValue
     *            expected value
     * @return restriction
     */
    public static Restriction ge(final FieldDefinition fieldDefinition, final Object expectedValue) {
        ValueAndError value = validateValue(fieldDefinition, expectedValue);
        if (!value.isValid()) {
            return null;
        }
        return new SimpleRestriction(fieldDefinition.getName(), value.getValue(), RestrictionOperator.GE);
    }

    /**
     * Create "greater than" restriction for given field's definition.
     * 
     * @param fieldDefinition
     *            field's definition
     * @param expectedValue
     *            expected value
     * @return restriction
     */
    public static Restriction gt(final FieldDefinition fieldDefinition, final Object expectedValue) {
        ValueAndError value = validateValue(fieldDefinition, expectedValue);
        if (!value.isValid()) {
            return null;
        }
        return new SimpleRestriction(fieldDefinition.getName(), value.getValue(), RestrictionOperator.GT);
    }

    /**
     * Create "is not null" restriction for given field's definition.
     * 
     * @param fieldDefinition
     *            field's definition
     * @return restriction
     */
    public static Restriction isNotNull(final FieldDefinition fieldDefinition) {
        return new IsNotNullRestriction(fieldDefinition.getName());
    }

    /**
     * Create "is null" restriction for given field's definition.
     * 
     * @param fieldDefinition
     *            field's definition
     * @return restriction
     */
    public static Restriction isNull(final FieldDefinition fieldDefinition) {
        return new IsNullRestriction(fieldDefinition.getName());
    }

    /**
     * Create "less or equals" restriction for given field's definition.
     * 
     * @param fieldDefinition
     *            field's definition
     * @param expectedValue
     *            expected value
     * @return restriction
     */
    public static Restriction le(final FieldDefinition fieldDefinition, final Object expectedValue) {
        ValueAndError value = validateValue(fieldDefinition, expectedValue);
        if (!value.isValid()) {
            return null;
        }
        return new SimpleRestriction(fieldDefinition.getName(), value.getValue(), RestrictionOperator.LE);
    }

    /**
     * Create "greater than" restriction for given field's definition.
     * 
     * @param fieldDefinition
     *            field's definition
     * @param expectedValue
     *            expected value
     * @return restriction
     */
    public static Restriction lt(final FieldDefinition fieldDefinition, final Object expectedValue) {
        ValueAndError value = validateValue(fieldDefinition, expectedValue);
        if (!value.isValid()) {
            return null;
        }
        return new SimpleRestriction(fieldDefinition.getName(), value.getValue(), RestrictionOperator.LT);
    }

    /**
     * Create "not equals" restriction for given field's definition.
     * 
     * @param fieldDefinition
     *            field's definition
     * @param expectedValue
     *            expected value
     * @return restriction
     */
    public static Restriction ne(final FieldDefinition fieldDefinition, final Object expectedValue) {
        if (expectedValue instanceof String && ((String) expectedValue).matches(".*[\\*%\\?_].*")) {
            String preperadValue = ((String) expectedValue).replace('*', '%').replace('?', '_');
            return not(new LikeRestriction(fieldDefinition.getName(), preperadValue));
        } else {
            ValueAndError value = validateValue(fieldDefinition, expectedValue);
            if (!value.isValid()) {
                return null;
            }
            return new SimpleRestriction(fieldDefinition.getName(), value.getValue(), RestrictionOperator.NE);
        }
    }

    public static Restriction not(final Restriction restriction) {
        return new LogicalOperatorRestriction(RestrictionLogicalOperator.NOT, restriction);
    }

    public static Restriction and(final Restriction... restrictions) {
        return new LogicalOperatorRestriction(RestrictionLogicalOperator.AND, restrictions);
    }

    public static Restriction or(final Restriction... restrictions) {
        return new LogicalOperatorRestriction(RestrictionLogicalOperator.OR, restrictions);
    }

    private static ValueAndError validateValue(final FieldDefinition fieldDefinition, final Object value) {
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
