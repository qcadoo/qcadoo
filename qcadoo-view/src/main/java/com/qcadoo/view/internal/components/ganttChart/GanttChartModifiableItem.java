package com.qcadoo.view.internal.components.ganttChart;

import com.qcadoo.view.api.components.ganttChart.GanttChartItem;

public interface GanttChartModifiableItem extends GanttChartItem {

    void setName(String name);

    void setDateFrom(String dateFrom);

    void setDateTo(String dateTo);

    void setFrom(double from);

    void setTo(double to);

}
