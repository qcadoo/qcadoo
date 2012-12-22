package com.qcadoo.view.api.components.ganttChart;

import com.qcadoo.view.internal.components.ganttChart.GanttChartItemStripImpl;

/**
 * Factory for gantt chart item's strips.
 * 
 * @author maku
 * @since 1.2.1
 */
public final class GanttChartItemStripFactory {

    private GanttChartItemStripFactory() {
    };

    /**
     * Creates item's strip with given color and size.
     * 
     * @param color
     *            css color string. For example: "red", "#45DFA9", "rgb(10, 55, 255)"
     * @param size
     *            percentage size of strip
     * @return new GanttChartItemStrip instance
     */
    public static GanttChartItemStrip create(final String color, final int size) {
        return new GanttChartItemStripImpl(color, size);
    }

}
