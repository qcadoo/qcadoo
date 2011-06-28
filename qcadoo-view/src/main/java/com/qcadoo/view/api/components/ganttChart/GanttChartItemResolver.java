package com.qcadoo.view.api.components.ganttChart;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONObject;

/**
 * @since 0.4.3
 */
public interface GanttChartItemResolver {

    Map<String, List<GanttChartItem>> resolve(GanttChartScale scale, JSONObject context, Locale locale);

}
