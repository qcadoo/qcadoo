package com.qcadoo.view.api.components.ganttChart;

import java.util.List;
import java.util.Map;

public interface GanttChartItemResolver {

    Map<String, List<GanttChartItem>> resolve(GanttChartScale scale);

}
