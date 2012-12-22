package com.qcadoo.view.api.components.ganttChart;

import org.json.JSONException;
import org.json.JSONObject;


public class GanttChartItemStripImpl implements GanttChartItemStrip {

    private final String color;

    private final int size;

    public GanttChartItemStripImpl(final String cssColor, final int size) {
        this.color = cssColor;
        this.size = size;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public JSONObject getAsJson() throws JSONException {
        final JSONObject json = new JSONObject();
        json.put("color", color);
        json.put("size", size);
        return json;
    }

}
