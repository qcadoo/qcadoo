package com.qcadoo.view.internal.components.ganttChart;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.qcadoo.localization.api.utils.DateUtils;
import com.qcadoo.view.api.components.ganttChart.GanttChartItem;

public class GanttChartItemFactory {

    private final int precision = 10;

    private final int interval;

    private final SimpleDateFormat format = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);

    public GanttChartItemFactory(final int interval) {
        this.interval = interval;
    }

    public GanttChartItem createGanttChartItem(final String rowName, final String name, final Date dateFrom, final Date dateTo,
            final Date itemDateFrom, final Date itemDateTo) {

        double from = getPosition(dateFrom, dateTo, itemDateFrom);
        double to = getPosition(dateFrom, dateTo, itemDateTo);

        if (from == to) {
            return null;
        }

        return new GanttChartItemImpl(rowName, name, format.format(itemDateFrom), format.format(itemDateTo), from, to);
    }

    private double getPosition(final Date dateFrom, final Date dateTo, final Date date) {
        long tmFrom = dateFrom.getTime();
        long tmItem = date.getTime();
        long tmTo = dateTo.getTime();

        int tmInterval = 1000 * 60 * 60 * interval;

        if (tmItem <= tmFrom) {
            return 0;
        }

        if (tmItem >= tmTo) {
            return (tmTo - tmFrom) / tmInterval;
        }

        int region = (int) (tmItem - tmFrom) / tmInterval;

        long tmRegion = tmFrom + (tmInterval * region);

        return ((int) ((region + ((double) (tmItem - tmRegion)) / tmInterval) * precision)) / (double) precision;
    }

}
