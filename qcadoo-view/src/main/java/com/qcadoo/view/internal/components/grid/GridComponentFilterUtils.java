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
package com.qcadoo.view.internal.components.grid;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.qcadoo.localization.api.utils.DateUtils;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.types.BelongsToType;

public class GridComponentFilterUtils {

    public static void addFilters(final Map<String, String> filters, final Map<String, GridComponentColumn> columns,
            final DataDefinition dataDefinition, final SearchCriteriaBuilder criteria) throws ParseException {
        for (Map.Entry<String, String> filter : filters.entrySet()) {

            String field = getFieldNameByColumnName(columns, filter.getKey());

            if (field != null) {
                FieldDefinition fieldDefinition = getFieldDefinition(dataDefinition, field);

                Map.Entry<GridComponentFilterOperator, String> filterValue = parseFilterValue(filter.getValue());

                if ("".equals(filterValue.getValue())) {
                    continue;
                }

                if (fieldDefinition != null && String.class.isAssignableFrom(fieldDefinition.getType().getType())) {
                    addStringFilter(criteria, filterValue, field);
                } else if (fieldDefinition != null && Boolean.class.isAssignableFrom(fieldDefinition.getType().getType())) {
                    addSimpleFilter(criteria, filterValue, field, "1".equals(filterValue.getValue()));
                } else if (fieldDefinition != null && Date.class.isAssignableFrom(fieldDefinition.getType().getType())) {
                    addDateFilter(criteria, filterValue, field);
                } else {
                    addSimpleFilter(criteria, filterValue, field, filterValue.getValue());
                }
            }
        }
    }

    private static void addSimpleFilter(final SearchCriteriaBuilder criteria,
            final Entry<GridComponentFilterOperator, String> filterValue, final String field, final Object value) {
        switch (filterValue.getKey()) {
            case EQ:
                criteria.isEq(field, value);
                break;
            case NE:
                criteria.isNe(field, value);
                break;
            case GT:
                criteria.isGt(field, value);
                break;
            case GE:
                criteria.isGe(field, value);
                break;
            case LT:
                criteria.isLt(field, value);
                break;
            case LE:
                criteria.isLe(field, value);
                break;
            default:
                throw new IllegalStateException("Unknown filter operator");
        }
    }

    private static void addStringFilter(final SearchCriteriaBuilder criteria,
            final Entry<GridComponentFilterOperator, String> filterValue, final String field) {
        String value = filterValue.getValue();

        switch (filterValue.getKey()) {
            case EQ:
            case NE:
            case GT:
            case LE:
                value += "*";
                break;
            default:
                break;
        }

        addSimpleFilter(criteria, filterValue, field, value);
    }

    private static void addDateFilter(final SearchCriteriaBuilder criteria,
            final Entry<GridComponentFilterOperator, String> filterValue, final String field) {
        Date minDate = null;
        Date maxDate = null;

        try {
            minDate = DateUtils.parseAndComplete(filterValue.getValue(), false);
            maxDate = DateUtils.parseAndComplete(filterValue.getValue(), true);
        } catch (ParseException e) {
            throw new IllegalStateException("Invalid filter date " + filterValue.getValue(), e);
        }

        if (minDate == null || maxDate == null) {
            throw new IllegalStateException("Invalid filter date " + filterValue.getValue());
        }

        switch (filterValue.getKey()) {
            case EQ:
                criteria.openAnd().isGe(field, minDate).isLe(field, maxDate).closeAnd();
                break;
            case NE:
                criteria.openOr().isLt(field, minDate).isGt(field, maxDate).closeOr();
                break;
            case GT:
                criteria.isGt(field, maxDate);
                break;
            case GE:
                criteria.isGe(field, minDate);
                break;
            case LT:
                criteria.isLt(field, minDate);
                break;
            case LE:
                criteria.isLe(field, maxDate);
                break;
            default:
                throw new IllegalStateException("Unknown filter operator");
        }
    }

    private static Map.Entry<GridComponentFilterOperator, String> parseFilterValue(final String filterValue) {
        GridComponentFilterOperator operator = GridComponentFilterOperator.EQ;
        String value;
        if (filterValue.charAt(0) == '>') {
            if (filterValue.length() > 1 && filterValue.charAt(1) == '=') {
                operator = GridComponentFilterOperator.GE;
                value = filterValue.substring(2);
            } else if (filterValue.length() > 1 && filterValue.charAt(1) == '<') {
                operator = GridComponentFilterOperator.NE;
                value = filterValue.substring(2);
            } else {
                operator = GridComponentFilterOperator.GT;
                value = filterValue.substring(1);
            }
        } else if (filterValue.charAt(0) == '<') {
            if (filterValue.length() > 1 && filterValue.charAt(1) == '=') {
                operator = GridComponentFilterOperator.LE;
                value = filterValue.substring(2);
            } else if (filterValue.length() > 1 && filterValue.charAt(1) == '>') {
                operator = GridComponentFilterOperator.NE;
                value = filterValue.substring(2);
            } else {
                operator = GridComponentFilterOperator.LT;
                value = filterValue.substring(1);
            }
        } else if (filterValue.charAt(0) == '=') {
            if (filterValue.length() > 1 && filterValue.charAt(1) == '<') {
                operator = GridComponentFilterOperator.LE;
                value = filterValue.substring(2);
            } else if (filterValue.length() > 1 && filterValue.charAt(1) == '>') {
                operator = GridComponentFilterOperator.GE;
                value = filterValue.substring(2);
            } else if (filterValue.length() > 1 && filterValue.charAt(1) == '=') {
                value = filterValue.substring(2);
            } else {
                value = filterValue.substring(1);
            }
        } else {
            value = filterValue;
        }
        return Collections.singletonMap(operator, value.trim()).entrySet().iterator().next();
    }

    private static FieldDefinition getFieldDefinition(DataDefinition dataDefinition, final String field) {
        String[] path = field.split("\\.");

        for (int i = 0; i < path.length; i++) {

            if (dataDefinition.getField(path[i]) == null) {
                return null;
            }

            FieldDefinition fieldDefinition = dataDefinition.getField(path[i]);

            if (i < path.length - 1) {
                if (fieldDefinition.getType() instanceof BelongsToType) {
                    dataDefinition = ((BelongsToType) fieldDefinition.getType()).getDataDefinition();
                    continue;
                } else {
                    return null;
                }
            }

            return fieldDefinition;
        }

        return null;
    }

    public static String getFieldNameByColumnName(final Map<String, GridComponentColumn> columns, final String columnName) {
        GridComponentColumn column = columns.get(columnName);

        if (column == null) {
            return null;
        }

        if (StringUtils.hasText(column.getExpression())) {
            Matcher matcher = Pattern.compile("#(\\w+)\\['(\\w+)'\\]").matcher(column.getExpression());
            if (matcher.matches()) {
                return matcher.group(1) + "." + matcher.group(2);
            }
        } else if (column.getFields().size() == 1) {
            return column.getFields().get(0).getName();
        }

        return null;
    }

}
