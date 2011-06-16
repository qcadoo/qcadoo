package com.qcadoo.view.api.components.ganttChart;

/**
 * @since 0.4.3
 */
public interface GanttChartItem {

    String getRowName();

    String getName();

    String getDateFrom();

    String getDateTo();

    double getFrom();

    double getTo();

}