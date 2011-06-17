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

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.model.internal.api.ValueAndError;
import com.qcadoo.model.internal.types.DateType;
import com.qcadoo.view.api.components.ganttChart.GanttChartItem;
import com.qcadoo.view.api.components.ganttChart.GanttChartItemResolver;
import com.qcadoo.view.internal.components.ganttChart.GanttChartScaleImpl.ZoomLevel;
import com.qcadoo.view.internal.states.AbstractComponentState;

public class GanttChartComponentState extends AbstractComponentState {

    public final GanttChartComponentEventPerformer eventPerformer = new GanttChartComponentEventPerformer();

    private GanttChartScaleImpl scale;

    private String dateFromErrorMessage;

    private String dateToErrorMessage;

    private String globalErrorMessage;

    Map<String, List<GanttChartItem>> items;

    protected static final DateType dateType = new DateType();

    private final GanttChartItemResolver itemResolver;

    private final int defaultStartDay;

    private final int defaultEndDay;

    private final ZoomLevel defaultZoomLevel;

    public GanttChartComponentState(final GanttChartItemResolver itemResolver, final ZoomLevel defaultZoomLevel,
            final int defaultStartDay, final int defaultEndDay) {
        this.itemResolver = itemResolver;
        this.defaultZoomLevel = defaultZoomLevel;
        this.defaultStartDay = defaultStartDay;
        this.defaultEndDay = defaultEndDay;
        registerEvent("refresh", eventPerformer, "refresh");
        registerEvent("initialize", eventPerformer, "initialize");
    }

    @Override
    protected void initializeContent(final JSONObject json) throws JSONException {

        ZoomLevel zoomLevel = ZoomLevel.valueOf(json.getString("scale"));

        String dateFromString = json.getString("dateFrom");
        String dateToString = json.getString("dateTo");

        DateTime now = new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);

        Date dateFrom = now.plusDays(defaultStartDay).toDate();
        Date dateTo = now.plusDays(defaultEndDay).toDate();

        if (dateFromString == null || "".equals(dateFromString)) {
            dateFromErrorMessage = translate("errorMessage.emptyDate");
        } else {
            ValueAndError dateFromVaE = dateType.toObject(null, dateFromString);
            if (dateFromVaE.getMessage() == null) {
                dateFrom = (Date) dateFromVaE.getValue();
            } else {
                dateFromErrorMessage = translate("errorMessage.dateNotValid");
            }
        }

        if (dateToString == null || "".equals(dateToString)) {
            dateToErrorMessage = translate("errorMessage.emptyDate");
        } else {
            ValueAndError dateToVaE = dateType.toObject(null, dateToString);
            if (dateToVaE.getMessage() == null) {
                dateTo = (Date) dateToVaE.getValue();
            } else {
                dateToErrorMessage = translate("errorMessage.dateNotValid");
            }
        }

        scale = new GanttChartScaleImpl(this, zoomLevel, dateFrom, dateTo);

        if (dateFromErrorMessage == null && globalErrorMessage == null) {
            if (scale.isFromLargerThanTo()) {
                globalErrorMessage = translate("errorMessage.fromLargerThanTo");
            } else if (scale.isTooLargeRange()) {
                globalErrorMessage = translate("errorMessage.tooLargeRange", "" + scale.getMaxRangeInMonths());
            }
        }

        requestRender();
        requestUpdateState();
    }

    @Override
    protected JSONObject renderContent() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("zoomLevel", scale.getZoomLevel().toString());

        json.put("dateFromErrorMessage", dateFromErrorMessage);
        json.put("dateToErrorMessage", dateToErrorMessage);
        if (dateFromErrorMessage == null) {
            json.put("dateFrom", dateType.toString(scale.getDateFrom(), getLocale()));
        }
        if (dateToErrorMessage == null) {
            json.put("dateTo", dateType.toString(scale.getDateTo(), getLocale()));
        }

        json.put("globalErrorMessage", globalErrorMessage);

        if (globalErrorMessage == null) {
            json.put("scale", scale.getAsJson());

            JSONArray rowsArray = new JSONArray();
            JSONArray itemsArray = new JSONArray();

            for (Map.Entry<String, List<GanttChartItem>> entry : items.entrySet()) {
                rowsArray.put(entry.getKey());
                for (GanttChartItem item : entry.getValue()) {
                    if (item != null) {
                        itemsArray.put(convertItemToJson(item));
                    }
                }
            }

            json.put("rows", rowsArray);
            json.put("items", itemsArray);
        }

        return json;
    }

    private JSONObject convertItemToJson(final GanttChartItem item) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("row", item.getRowName());
        json.put("from", item.getFrom());
        json.put("to", item.getTo());

        // type: 'disabled',

        JSONObject info = new JSONObject();
        info.put("name", item.getName());
        info.put("dateFrom", item.getDateFrom());
        info.put("dateTo", item.getDateTo());
        json.put("info", info);

        return json;
    }

    protected String translate(final String suffix, final String... args) {
        return getTranslationService().translate(getTranslationPath() + "." + suffix, "qcadooView.gantt." + suffix, getLocale(),
                args);
    }

    protected class GanttChartComponentEventPerformer {

        public void initialize(final String[] args) {
            DateTime now = new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            scale = new GanttChartScaleImpl(GanttChartComponentState.this, defaultZoomLevel, now.plusDays(defaultStartDay)
                    .toDate(), now.plusDays(defaultEndDay).toDate());
            dateFromErrorMessage = null;
            dateToErrorMessage = null;
            globalErrorMessage = null;
            refresh(args);
        }

        public void refresh(final String[] args) {
            if (globalErrorMessage != null) {
                return;
            }

            items = itemResolver.resolve(scale);
        }
    }

}
