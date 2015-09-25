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

import com.google.common.base.Preconditions;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.expression.ExpressionUtils;
import com.qcadoo.plugin.api.PluginUtils;
import com.qcadoo.view.constants.Alignment;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GridComponentColumn {

    private static final int DEFAULT_COLUMN_WIDTH = 100;

    private final String extendingPluginIdentifier;

    private final String name;

    private final List<FieldDefinition> fields = new ArrayList<FieldDefinition>();

    private String expression;

    private Integer width = DEFAULT_COLUMN_WIDTH;

    private boolean link;

    private boolean hidden;

    private Alignment align;

    public GridComponentColumn(final String name) {
        this(name, null);
    }

    public GridComponentColumn(final String name, final String extendingPluginIdentifier) {
        this.name = name;
        this.extendingPluginIdentifier = extendingPluginIdentifier;
    }

    public String getName() {
        return name;
    }

    public void setWidth(final Integer width) {
        this.width = width;
    }

    public Integer getWidth() {
        return width;
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(final boolean link) {
        this.link = link;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setAlign(final Alignment align) {
        this.align = align;
    }

    public Alignment getAlign() {
        Alignment effectiveAlign = align;
        if (effectiveAlign == null) {
            if (fields.size() == 1 && Number.class.isAssignableFrom(fields.get(0).getType().getType())) {
                effectiveAlign = Alignment.RIGHT;
            } else {
                effectiveAlign = Alignment.LEFT;
            }
        }
        Preconditions.checkState(effectiveAlign != null,
                "getAlign() should never returns null. It seems to be race condition issue..");
        return effectiveAlign;
    }

    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }

    public void setExpression(final String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void addField(final FieldDefinition field) {
        fields.add(field);
    }

    public List<FieldDefinition> getFields() {
        return fields;
    }

    public String getValue(final Entity entity, final Locale locale) {
        if (StringUtils.hasText(expression)) {
            return ExpressionUtils.getValue(entity, expression, locale);
        } else {
            String value = ExpressionUtils.getValue(entity, fields, locale);
            if (value != null) {
                value = value.replaceAll("\n", " ");
            }
            return value;
        }
    }

    public boolean isVisibleForCurrentTenant() {
        return extendingPluginIdentifier == null || PluginUtils.isEnabled(extendingPluginIdentifier);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(fields).append(extendingPluginIdentifier).append(hidden).append(link)
                .append(expression).append(width).append(getAlign()).toHashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GridComponentColumn that = (GridComponentColumn) o;
        return new EqualsBuilder().append(this.name, that.name).append(this.fields, that.fields)
                .append(this.extendingPluginIdentifier, that.extendingPluginIdentifier).append(this.hidden, that.hidden)
                .append(this.link, that.link).append(this.expression, that.expression).append(this.width, that.width)
                .append(this.getAlign(), that.getAlign()).isEquals();
    }

}
