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

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.model.internal.api.ValueAndError;
import com.qcadoo.model.internal.types.DateType;
import com.qcadoo.view.internal.states.AbstractComponentState;

public class GanttChartComponentState extends AbstractComponentState {

    public final GanttChartComponentEventPerformer eventPerformer = new GanttChartComponentEventPerformer();

    private enum ZoomLevel {
        H1(1), H3(3), H6(6), D1(24);

        private final int maxRangeInMonths;

        private ZoomLevel(final int maxRangeInMonths) {
            this.maxRangeInMonths = maxRangeInMonths;
        }

        public int getMaxRangeInMonths() {
            return maxRangeInMonths;
        }
    }

    private ZoomLevel currentZoomLevel;

    private Date dateFrom;

    private Date dateTo;

    private String dateFromErrorMessage;

    private String dateToErrorMessage;

    private String globalErrorMessage;

    private List<String> rows;

    private List<GanttChartItem> items;

    private static final DateType dateType = new DateType();

    public GanttChartComponentState() {
        registerEvent("refresh", eventPerformer, "refresh");
        registerEvent("initialize", eventPerformer, "initialize");
    }

    @Override
    protected void initializeContent(JSONObject json) throws JSONException {

        currentZoomLevel = ZoomLevel.valueOf(json.getString("scale"));

        String dateFromString = json.getString("dateFrom");
        String dateToString = json.getString("dateTo");

        if (dateFromString == null || "".equals(dateFromString)) {
            dateFrom = null;
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
            dateTo = null;
            dateToErrorMessage = translate("errorMessage.emptyDate");
        } else {
            ValueAndError dateToVaE = dateType.toObject(null, dateToString);
            if (dateToVaE.getMessage() == null) {
                dateTo = (Date) dateToVaE.getValue();
            } else {
                dateToErrorMessage = translate("errorMessage.dateNotValid");
            }
        }

        if (dateFromErrorMessage == null && globalErrorMessage == null) {
            validateDatesRange();
        }

        requestRender();
        requestUpdateState();
    }

    @Override
    protected JSONObject renderContent() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("zoomLevel", currentZoomLevel.toString());

        json.put("dateFromErrorMessage", dateFromErrorMessage);
        json.put("dateToErrorMessage", dateToErrorMessage);
        if (dateFromErrorMessage == null) {
            json.put("dateFrom", dateType.toString(dateFrom, getLocale()));
        }
        if (dateToErrorMessage == null) {
            json.put("dateTo", dateType.toString(dateTo, getLocale()));
        }

        json.put("globalErrorMessage", globalErrorMessage);

        if (globalErrorMessage == null) {
            json.put("scale", calculateScale());

            JSONArray rowsArray = new JSONArray();
            for (String row : rows) {
                rowsArray.put(row);
            }
            json.put("rows", rowsArray);

            JSONArray itemsArray = new JSONArray();
            for (GanttChartItem item : items) {
                itemsArray.put(item.getAsJson());
            }
            json.put("items", itemsArray);
        }

        return json;
    }

    private JSONObject calculateScale() throws JSONException {
        JSONObject scaleObject = new JSONObject();
        JSONArray categoriesArray = null;
        switch (currentZoomLevel) {
            case H1: {
                categoriesArray = getDaysArray();
                scaleObject.put("elementsInCategory", 24);
                scaleObject.put("elementLabelsInterval", 1);
                scaleObject.put("elementLabelInitialNumber", 0);
                break;
            }
            case H3: {
                categoriesArray = getDaysArray();
                scaleObject.put("elementsInCategory", 8);
                scaleObject.put("elementLabelsInterval", 3);
                scaleObject.put("elementLabelInitialNumber", 0);
                break;
            }
            case H6: {
                categoriesArray = getDaysArray();
                scaleObject.put("elementsInCategory", 4);
                scaleObject.put("elementLabelsInterval", 6);
                scaleObject.put("elementLabelInitialNumber", 0);
                break;
            }
            case D1: {
                return getWeeksScale();
            }
        }
        scaleObject.put("categories", categoriesArray);
        return scaleObject;
    }

    private void validateDatesRange() {
        if (dateFrom != null && dateTo != null && dateFrom.compareTo(dateTo) > 0) {
            globalErrorMessage = translate("errorMessage.fromLargerThanTo");
            return;
        }
        DateTime dateTimeFrom = new DateTime(dateFrom);
        DateTime dateTimeTo = new DateTime(dateTo);
        if (dateTimeFrom.plusMonths(currentZoomLevel.getMaxRangeInMonths()).compareTo(dateTimeTo) < 0) {
            globalErrorMessage = translate("errorMessage.tooLargeRange", "" + currentZoomLevel.getMaxRangeInMonths());
            return;
        }
    }

    private JSONArray getDaysArray() {
        JSONArray daysArray = new JSONArray();
        DateTime dateTimeFrom = new DateTime(dateFrom);
        DateTime dateTimeTo = new DateTime(dateTo);
        while (dateTimeFrom.compareTo(dateTimeTo) <= 0) {
            daysArray.put(dateType.toString(dateTimeFrom.toDate(), getLocale()));
            dateTimeFrom = dateTimeFrom.plusDays(1);
        }
        return daysArray;
    }

    private JSONObject getWeeksScale() throws JSONException {

        DateTime dateTimeFrom = new DateTime(dateFrom);
        DateTime dateTimeTo = new DateTime(dateTo);
        int dateTimeFromDayOfWeek = dateTimeFrom.getDayOfWeek();
        int dateTimeToDayOfWeek = dateTimeTo.getDayOfWeek();

        List<Integer> weekNumbers = new ArrayList<Integer>();

        int lastAddedWeekNumber = 0;
        while (dateTimeFrom.compareTo(dateTimeTo) < 0) {
            lastAddedWeekNumber = dateTimeFrom.getWeekOfWeekyear();
            weekNumbers.add(lastAddedWeekNumber);
            dateTimeFrom = dateTimeFrom.plusWeeks(1);
        }
        if (dateTimeTo.getWeekOfWeekyear() > lastAddedWeekNumber) {
            lastAddedWeekNumber = dateTimeTo.getWeekOfWeekyear();
            weekNumbers.add(lastAddedWeekNumber);
        }

        JSONArray weeksArray = new JSONArray();
        for (int i = 0; i < weekNumbers.size(); i++) {
            if (i == 0 && dateTimeFromDayOfWeek > 5) {
                weeksArray.put("" + weekNumbers.get(i));
            } else if (i == weekNumbers.size() - 1 && dateTimeToDayOfWeek < 3) {
                weeksArray.put("" + weekNumbers.get(i));
            } else {
                weeksArray.put(translate("week") + " " + weekNumbers.get(i));
            }

        }

        JSONObject scaleObject = new JSONObject();
        JSONArray weekDays = new JSONArray();
        weekDays.put(translate("weekDay.short.monday"));
        weekDays.put(translate("weekDay.short.tuesday"));
        weekDays.put(translate("weekDay.short.wensday"));
        weekDays.put(translate("weekDay.short.thursday"));
        weekDays.put(translate("weekDay.short.friday"));
        weekDays.put(translate("weekDay.short.saturday"));
        weekDays.put(translate("weekDay.short.sunday"));
        scaleObject.put("elementsInCategory", 7);
        scaleObject.put("elementLabelsValues", weekDays);
        scaleObject.put("firstCategoryFirstElement", dateTimeFromDayOfWeek);
        scaleObject.put("lastCategoryLastElement", dateTimeToDayOfWeek);

        scaleObject.put("categories", weeksArray);

        return scaleObject;
    }

    private String translate(String suffix, String... args) {
        return getTranslationService().translate(getTranslationPath() + "." + suffix, "qcadooView.gantt." + suffix, getLocale(),
                args);
    }

    protected class GanttChartComponentEventPerformer {

        public void initialize(final String[] args) {
            currentZoomLevel = ZoomLevel.H3;
            DateTime now = new DateTime();
            dateFrom = now.toDate();
            dateTo = now.plusDays(21).toDate();
            dateFromErrorMessage = null;
            dateToErrorMessage = null;
            globalErrorMessage = null;
            refresh(args);
        }

        public void refresh(final String[] args) {
            if (globalErrorMessage != null) {
                return;
            }
            // TODO set elements
            rows = new LinkedList<String>();
            for (int i = 1; i < 30; i++) {
                rows.add("row" + i);
            }
            items = new LinkedList<GanttChartItem>();
            GanttChartItem gi1 = new GanttChartItem("row3", 5, 10.5);
            gi1.setName("test name 1");
            gi1.setDateFrom("10-20-2011");
            gi1.setDateTo("15-20-2011");
            items.add(gi1);

            GanttChartItem gi2 = new GanttChartItem("row4", 4, 5.5);
            gi2.setName("test name 2");
            gi2.setDateFrom("10-20-2011");
            gi2.setDateTo("15-20-2011");
            items.add(gi2);

            GanttChartItem gi3 = new GanttChartItem("row5", 1, 5.5);
            gi3.setName("test name 3");
            gi3.setDateFrom("10-20-2011");
            gi3.setDateTo("15-20-2011");
            items.add(gi3);

            GanttChartItem gi4 = new GanttChartItem("row5", 6.5, 11);
            gi4.setName("very very long test name number 4");
            gi4.setDateFrom("10-20-2011");
            gi4.setDateTo("15-20-2011");
            items.add(gi4);

            GanttChartItem gi5 = new GanttChartItem("row7", 6.5, 31);
            gi5.setName("very very long test name number 5");
            gi5.setDateFrom("10-20-2011");
            gi5.setDateTo("15-20-2011");
            items.add(gi5);

            GanttChartItem gi6 = new GanttChartItem("row9", 16, 29.5);
            gi6.setName("very very long test name number 6");
            gi6.setDateFrom("10-20-2011");
            gi6.setDateTo("15-20-2011");
            items.add(gi6);

        }
    }

}
