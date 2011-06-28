package com.qcadoo.view.api.components.ganttChart;

import java.util.Date;

/**
 * @since 0.4.3
 */
public interface GanttChartScale {

    Date getDateTo();

    void setDateTo(Date dateTo);

    Date getDateFrom();

    void setDateFrom(Date dateFrom);

    GanttChartItem createGanttChartItem(final String rowName, final String name, final Long entityId, final Date dateFrom,
            final Date dateTo);

}