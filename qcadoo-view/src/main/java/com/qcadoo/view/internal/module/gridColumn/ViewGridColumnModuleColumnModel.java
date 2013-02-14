/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.2.0
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
package com.qcadoo.view.internal.module.gridColumn;

public class ViewGridColumnModuleColumnModel {

    private final String name;

    private final String fields;

    private String expression;

    private Integer width;

    private boolean link = false;

    private boolean searchable = false;

    private boolean orderable = false;

    public ViewGridColumnModuleColumnModel(final String name, final String fields) {
        this.name = name;
        this.fields = fields;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(final String expression) {
        this.expression = expression;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(final Integer width) {
        this.width = width;
    }

    public boolean getLink() {
        return link;
    }

    public void setLink(final boolean link) {
        this.link = link;
    }

    public boolean getSearchable() {
        return searchable;
    }

    public void setSearchable(final boolean searchable) {
        this.searchable = searchable;
    }

    public boolean getOrderable() {
        return orderable;
    }

    public void setOrderable(final Boolean orderable) {
        this.orderable = orderable;
    }

    public String getName() {
        return name;
    }

    public String getFields() {
        return fields;
    }

}
