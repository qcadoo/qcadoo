package com.qcadoo.view.api.components.ganttChart;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @since 0.4.3
 */
public interface GanttChartItem {

    String getRowName();

    String getName();

    Long getEntityId();

    String getDateFrom();

    String getDateTo();

    double getFrom();

    double getTo();

    JSONObject getAsJson() throws JSONException;

}