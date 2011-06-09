package com.qcadoo.view.internal.components.ganttChart;

import java.util.ArrayList;
import java.util.Date;
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

            JSONArray rows = new JSONArray();
            for (int i = 1; i < 10; i++) {
                rows.put("row" + i);
            }
            json.put("rows", rows);
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
            DateTime dt = new DateTime();
            dateFrom = dt.toDate();
            dateTo = dt.plusDays(21).toDate();
            dateFromErrorMessage = null;
            dateToErrorMessage = null;
            globalErrorMessage = null;
            refresh(args);
        }

        public void refresh(final String[] args) {
            System.out.println("---------- refresh");
            if (globalErrorMessage != null) {
                System.out.println("error: " + globalErrorMessage);
                return;
            }
            // TODO set elements

            System.out.println(currentZoomLevel.toString());
            System.out.println("---------- refresh");
        }
    }

}
