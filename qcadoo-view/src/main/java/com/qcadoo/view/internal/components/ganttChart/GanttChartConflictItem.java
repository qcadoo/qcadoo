package com.qcadoo.view.internal.components.ganttChart;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.view.api.components.ganttChart.GanttChartItem;

public class GanttChartConflictItem extends GanttChartItemImpl {

    private final List<GanttChartItem> items = new ArrayList<GanttChartItem>();

    public GanttChartConflictItem(String row, String dateFrom, String dateTo, double from, double to) {
        super(row, null, null, dateFrom, dateTo, from, to);
    }

    public void addItem(final GanttChartItem item) {
        items.add(item);
    }

    @Override
    public JSONObject getAsJson() throws JSONException {
        JSONObject json = super.getAsJson();

        JSONArray itemsArray = new JSONArray();
        for (GanttChartItem item : items) {
            itemsArray.put(item.getAsJson());
        }
        json.put("items", itemsArray);

        return json;
    }
}
