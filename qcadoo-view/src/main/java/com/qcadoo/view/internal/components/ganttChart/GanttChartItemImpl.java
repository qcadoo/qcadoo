/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.2
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
package com.qcadoo.view.internal.components.ganttChart;

import com.qcadoo.view.api.components.ganttChart.GanttChartItem;

public class GanttChartItemImpl implements GanttChartItem {

    private final String rowName;

    private final double from;

    private final double to;

    private final String name;

    private final String dateFrom;

    private final String dateTo;

    public GanttChartItemImpl(final String row, final String name, final String dateFrom, final String dateTo, final double from,
            final double to) {
        this.rowName = row;
        this.name = name;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.from = from;
        this.to = to;
    }

    @Override
    public String getRowName() {
        return rowName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDateFrom() {
        return dateFrom;
    }

    @Override
    public String getDateTo() {
        return dateTo;
    }

    @Override
    public double getFrom() {
        return from;
    }

    @Override
    public double getTo() {
        return to;
    }

}
