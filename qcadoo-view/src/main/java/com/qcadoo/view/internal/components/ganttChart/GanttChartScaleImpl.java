package com.qcadoo.view.internal.components.ganttChart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.model.internal.types.DateType;
import com.qcadoo.view.api.components.ganttChart.GanttChartItem;
import com.qcadoo.view.api.components.ganttChart.GanttChartScale;

public class GanttChartScaleImpl implements GanttChartScale {

    private final GanttChartComponentState ganttChartComponentState;

    private final GanttChartItemFactory ganttChartItemFactory;

    private static final DateType dateType = new DateType();

    private final ZoomLevel zoomLevel;

    private Date dateFrom;

    private Date dateTo;

    public enum ZoomLevel {
        H1(1, 1), H3(3, 3), H6(6, 6), D1(24, 24);

        private final int maxRangeInMonths;

        private final int hoursInterval;

        private ZoomLevel(final int maxRangeInMonths, final int hoursInterval) {
            this.maxRangeInMonths = maxRangeInMonths;
            this.hoursInterval = hoursInterval;
        }

        public int getHoursInterval() {
            return hoursInterval;
        }

        public int getMaxRangeInMonths() {
            return maxRangeInMonths;
        }
    }

    public GanttChartScaleImpl(final GanttChartComponentState ganttChartComponentState, final ZoomLevel zoomLevel,
            final Date dateFrom, final Date dateTo) {
        this.ganttChartComponentState = ganttChartComponentState;
        this.zoomLevel = zoomLevel;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.ganttChartItemFactory = new GanttChartItemFactory(zoomLevel.getHoursInterval());
    }

    public JSONObject getAsJson() throws JSONException {
        JSONObject scaleObject = new JSONObject();
        JSONArray categoriesArray = null;
        switch (zoomLevel) {
            case H1:
                categoriesArray = getDaysArray();
                scaleObject.put("elementsInCategory", 24);
                scaleObject.put("elementLabelsInterval", 1);
                scaleObject.put("elementLabelInitialNumber", 0);
                break;
            case H3:
                categoriesArray = getDaysArray();
                scaleObject.put("elementsInCategory", 8);
                scaleObject.put("elementLabelsInterval", 3);
                scaleObject.put("elementLabelInitialNumber", 0);
                break;
            case H6:
                categoriesArray = getDaysArray();
                scaleObject.put("elementsInCategory", 4);
                scaleObject.put("elementLabelsInterval", 6);
                scaleObject.put("elementLabelInitialNumber", 0);
                break;
            case D1:
                return getWeeksScale();
        }
        scaleObject.put("categories", categoriesArray);
        return scaleObject;
    }

    private JSONArray getDaysArray() {
        JSONArray daysArray = new JSONArray();
        DateTime dateTimeFrom = new DateTime(dateFrom);
        DateTime dateTimeTo = new DateTime(dateTo);
        while (dateTimeFrom.compareTo(dateTimeTo) <= 0) {
            daysArray.put(dateType.toString(dateTimeFrom.toDate(), ganttChartComponentState.getLocale()));
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
                weeksArray.put(ganttChartComponentState.translate("week") + " " + weekNumbers.get(i));
            }

        }

        JSONObject scaleObject = new JSONObject();
        JSONArray weekDays = new JSONArray();
        weekDays.put(ganttChartComponentState.translate("weekDay.short.monday"));
        weekDays.put(ganttChartComponentState.translate("weekDay.short.tuesday"));
        weekDays.put(ganttChartComponentState.translate("weekDay.short.wensday"));
        weekDays.put(ganttChartComponentState.translate("weekDay.short.thursday"));
        weekDays.put(ganttChartComponentState.translate("weekDay.short.friday"));
        weekDays.put(ganttChartComponentState.translate("weekDay.short.saturday"));
        weekDays.put(ganttChartComponentState.translate("weekDay.short.sunday"));
        scaleObject.put("elementsInCategory", 7);
        scaleObject.put("elementLabelsValues", weekDays);
        scaleObject.put("firstCategoryFirstElement", dateTimeFromDayOfWeek);
        scaleObject.put("lastCategoryLastElement", dateTimeToDayOfWeek);

        scaleObject.put("categories", weeksArray);

        return scaleObject;
    }

    public boolean isFromLargerThanTo() {
        return (dateFrom != null && dateTo != null && dateFrom.compareTo(dateTo) > 0);
    }

    public boolean isTooLargeRange() {
        DateTime dateTimeFrom = new DateTime(dateFrom);
        DateTime dateTimeTo = new DateTime(dateTo);
        return (dateTimeFrom.plusMonths(zoomLevel.getMaxRangeInMonths()).compareTo(dateTimeTo) < 0);
    }

    public int getMaxRangeInMonths() {
        return zoomLevel.getMaxRangeInMonths();
    }

    public ZoomLevel getZoomLevel() {
        return zoomLevel;
    }

    @Override
    public Date getDateTo() {
        return dateTo;
    }

    @Override
    public Date getDateFrom() {
        return dateFrom;
    }

    @Override
    public void setDateFrom(final Date dateFrom) {
        this.dateFrom = new DateTime(dateFrom).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0)
                .toDate();
    }

    @Override
    public void setDateTo(final Date dateTo) {
        this.dateTo = new DateTime(dateTo).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0)
                .toDate();
    }

    @Override
    public GanttChartItem createGanttChartItem(final String rowName, final String name, final Long entityId,
            final Date itemDateFrom, final Date itemDateTo) {
        return ganttChartItemFactory.createGanttChartItem(rowName, name, entityId, dateFrom, dateTo, itemDateFrom, itemDateTo);
    }

}