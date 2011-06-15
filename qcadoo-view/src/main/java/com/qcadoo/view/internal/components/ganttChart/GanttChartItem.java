package com.qcadoo.view.internal.components.ganttChart;

import org.json.JSONException;
import org.json.JSONObject;

public class GanttChartItem {

    private String row;

    private double from;

    private double to;

    private String name;

    private String dateFrom;

    private String dateTo;

    public GanttChartItem(String row, double from, double to) {
        this.row = row;
        this.from = from;
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public JSONObject getAsJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("row", row);
        json.put("from", from);
        json.put("to", to);

        // type: 'disabled',

        JSONObject info = new JSONObject();
        info.put("name", name);
        info.put("dateFrom", dateFrom);
        info.put("dateTo", dateTo);
        json.put("info", info);

        return json;
    }

}
