/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.3
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

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.Lists;
import com.qcadoo.view.api.components.ganttChart.GanttChartItemStrip;

public class GanttChartItemImpl implements GanttChartModifiableItem {

    private final String rowName;

    private final Long entityId;

    private double from;

    private double to;

    private String name;

    private String dateFrom;

    private String dateTo;

    private final List<GanttChartItemStrip> bgStrips;

    public GanttChartItemImpl(final String row, final String name, final Long entityId, final String dateFrom,
            final String dateTo, final double from, final double to) {
        this.bgStrips = Lists.newLinkedList();
        this.rowName = row;
        this.name = name;
        this.entityId = entityId;
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

    @Override
    public void setFrom(final double from) {
        this.from = from;
    }

    @Override
    public void setTo(final double to) {
        this.to = to;
    }

    @Override
    public void setDateFrom(final String dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public void setDateTo(final String dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public Long getEntityId() {
        return entityId;
    }

    @Override
    public void addBackgroundStrip(final GanttChartItemStrip bgStrip) {
        bgStrips.add(bgStrip);
    }

    @Override
    public JSONObject getAsJson() throws JSONException {
        final JSONObject json = new JSONObject();
        json.put("row", getRowName());
        json.put("id", getEntityId());
        json.put("from", getFrom());
        json.put("to", getTo());

        final JSONObject info = new JSONObject();
        info.put("name", getName());
        info.put("dateFrom", getDateFrom());
        info.put("dateTo", getDateTo());
        json.put("info", info);

        final JSONArray strips = new JSONArray();
        for (final GanttChartItemStrip strip : bgStrips) {
            strips.put(strip.getAsJson());
        }
        json.put("strips", strips);

        return json;
    }

    @Override
    public String toString() {
        return "GanttChartItemImpl [rowName=" + rowName + ", entityId=" + entityId + ", from=" + from + ", to=" + to + ", name="
                + name + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]";
    }

}
