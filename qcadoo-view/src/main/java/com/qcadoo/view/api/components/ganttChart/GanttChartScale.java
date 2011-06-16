package com.qcadoo.view.api.components.ganttChart;

import java.util.Date;


public interface GanttChartScale {

    Date getDateTo();

    Date getDateFrom();

    GanttChartItem createGanttChartItem(final String rowName, final String name, final Date dateFrom, final Date dateTo);

}