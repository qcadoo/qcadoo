/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.4
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

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.Sets;

public class GridComponentMultiSearchFilter {

    public static final String JSON_GROUP_OPERATOR_FIELD = "groupOp";

    public static final String JSON_RULES_FIELD = "rules";

    private GridComponentFilterGroupOperator groupOperator = null;

    private final Set<GridComponentMultiSearchFilterRule> rules = Sets.newHashSet();

    public GridComponentFilterGroupOperator getGroupOperator() {
        if (groupOperator == null) {
            throw new IllegalStateException("Filter grouping operator not defined.");
        }
        return groupOperator;
    }

    public void setGroupOperator(String operator) {
        if (GridComponentFilterGroupOperator.OR.getValue().equals(operator)) {
            groupOperator = GridComponentFilterGroupOperator.OR;
        } else if (GridComponentFilterGroupOperator.AND.getValue().equals(operator)) {
            groupOperator = GridComponentFilterGroupOperator.AND;
        } else {
            throw new IllegalStateException("Unwknow filter grouping operator.");
        }
    }

    public void addRule(final String field, final String operator, final String data) {
        GridComponentFilterOperator filterOperator = resolveOperator(operator);
        rules.add(new GridComponentMultiSearchFilterRule(field, filterOperator, data));
    }

    public Set<GridComponentMultiSearchFilterRule> getRules() {
        return rules;
    }

    public void clear() {
        groupOperator = null;
        rules.clear();
    }

    private GridComponentFilterOperator resolveOperator(final String operator) {
        GridComponentFilterOperator filterOperator;
        if (GridComponentFilterOperator.EQ.getValue().equals(operator)) {
            filterOperator = GridComponentFilterOperator.EQ;
        } else if (GridComponentFilterOperator.NE.getValue().equals(operator)) {
            filterOperator = GridComponentFilterOperator.NE;
        } else if (GridComponentFilterOperator.LT.getValue().equals(operator)) {
            filterOperator = GridComponentFilterOperator.LT;
        } else if (GridComponentFilterOperator.LE.getValue().equals(operator)) {
            filterOperator = GridComponentFilterOperator.LE;
        } else if (GridComponentFilterOperator.GT.getValue().equals(operator)) {
            filterOperator = GridComponentFilterOperator.GT;
        } else if (GridComponentFilterOperator.GE.getValue().equals(operator)) {
            filterOperator = GridComponentFilterOperator.GE;
        } else if (GridComponentFilterOperator.IN.getValue().equals(operator)) {
            filterOperator = GridComponentFilterOperator.IN;
        } else if (GridComponentFilterOperator.CN.getValue().equals(operator)) {
            filterOperator = GridComponentFilterOperator.CN;
        } else if (GridComponentFilterOperator.BW.getValue().equals(operator)) {
            filterOperator = GridComponentFilterOperator.BW;
        } else if (GridComponentFilterOperator.EW.getValue().equals(operator)) {
            filterOperator = GridComponentFilterOperator.EW;
        } else {
            throw new IllegalStateException("Unwknow filter operator.");
        }

        return filterOperator;
    }

    public JSONObject toJson() throws JSONException {
        if (groupOperator != null && !rules.isEmpty()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JSON_GROUP_OPERATOR_FIELD, groupOperator.getValue());
            JSONArray jsonRules = new JSONArray();
            for (GridComponentMultiSearchFilterRule rule : rules) {
                JSONObject jsonRule = new JSONObject();
                jsonRule.put(GridComponentMultiSearchFilterRule.JSON_FIELD_FIELD, rule.getField());
                jsonRule.put(GridComponentMultiSearchFilterRule.JSON_OPERATOR_FIELD, rule.getFilterOperator().getValue());
                jsonRule.put(GridComponentMultiSearchFilterRule.JSON_DATA_FIELD, rule.getData());
                jsonRules.put(jsonRule);
            }
            jsonObject.put(JSON_RULES_FIELD, jsonRules);
            return jsonObject;
        }
        return null;
    }
}
