package com.qcadoo.view.api.components.ganttChart;

import java.util.List;
import java.util.Map;

/**
 * @since 0.4.3
 */
public interface GanttChartItemResolver {

    Map<String, List<GanttChartItem>> resolve(GanttChartScale scale);

}
