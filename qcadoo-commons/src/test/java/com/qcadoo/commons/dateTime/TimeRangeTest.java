package com.qcadoo.commons.dateTime;

import junit.framework.Assert;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class TimeRangeTest {

    @Test
    public final void shouldCreateTimeRange() {
        // given
        LocalTime startTime = new LocalTime(10, 30, 0);
        LocalTime endTime = new LocalTime(14, 45, 30);

        // when
        TimeRange timeRange = new TimeRange(startTime, endTime);

        // then
        Assert.assertEquals(startTime, timeRange.getFrom());
        Assert.assertEquals(endTime, timeRange.getTo());

        Assert.assertFalse(timeRange.startsDayBefore());

        Assert.assertFalse(timeRange.contains(startTime.minusHours(2)));
        Assert.assertTrue(timeRange.contains(startTime));
        Assert.assertTrue(timeRange.contains(startTime.plusHours(2)));
        Assert.assertTrue(timeRange.contains(endTime));
        Assert.assertFalse(timeRange.contains(endTime.plusHours(2)));

        LocalDate today = LocalDate.now();
        Assert.assertEquals(new Interval(today.toDateTime(startTime), today.toDateTime(endTime)), timeRange.toInterval(today));
    }

    @Test
    public final void shouldCreateTimeRangeStartingDayBefore() {
        // given
        LocalTime startTime = new LocalTime(21, 0, 0);
        LocalTime endTime = new LocalTime(10, 0, 0);

        // when
        TimeRange timeRange = new TimeRange(startTime, endTime);

        // then
        Assert.assertEquals(startTime, timeRange.getFrom());
        Assert.assertEquals(endTime, timeRange.getTo());

        Assert.assertTrue(timeRange.startsDayBefore());

        Assert.assertFalse(timeRange.contains(startTime.minusHours(2)));
        Assert.assertTrue(timeRange.contains(startTime));
        Assert.assertTrue(timeRange.contains(startTime.plusHours(2)));
        Assert.assertTrue(timeRange.contains(endTime));
        Assert.assertFalse(timeRange.contains(endTime.plusHours(2)));

        LocalDate today = LocalDate.now();
        Assert.assertEquals(new Interval(today.toDateTime(startTime), today.toDateTime(endTime).plusDays(1)),
                timeRange.toInterval(today));
    }

}
