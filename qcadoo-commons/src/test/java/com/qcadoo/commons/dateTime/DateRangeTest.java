package com.qcadoo.commons.dateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Test;

import com.google.common.collect.Sets;

public class DateRangeTest {

    private static final DateTime DATE_FROM_PROTOTYPE = new DateTime(2013, 1, 1, 0, 0, 0, 0);

    private static final DateTime DATE_TO_PROTOTYPE = new DateTime(2013, 5, 10, 0, 0, 0, 0);

    @Test
    public final void shouldGettersReturnDefensiveCopyOfDates() {
        // given
        Date originalFrom = DATE_FROM_PROTOTYPE.toDate();
        Date originalTo = DATE_TO_PROTOTYPE.toDate();

        DateRange dateRange = new DateRange(originalFrom, originalTo);

        Date returnedFrom = dateRange.getFrom();
        Date returnedTo = dateRange.getTo();

        // when
        originalFrom.setTime(1000);
        originalTo.setTime(2000);
        returnedFrom.setTime(3000);
        returnedTo.setTime(4000);

        // then
        assertEquals(DATE_FROM_PROTOTYPE.toDate(), dateRange.getFrom());
        assertEquals(DATE_TO_PROTOTYPE.toDate(), dateRange.getTo());

        assertFalse(originalFrom.equals(dateRange.getFrom()));
        assertFalse(originalTo.equals(dateRange.getTo()));

        assertFalse(returnedFrom.equals(dateRange.getFrom()));
        assertFalse(returnedTo.equals(dateRange.getTo()));

        assertFalse(originalFrom.equals(returnedFrom));
        assertFalse(originalTo.equals(returnedTo));
    }

    @Test
    public final void shouldHashCodeAndEqualsBeImplementedCorrectly() {
        // given
        DateRange firstDateRange = new DateRange(DATE_FROM_PROTOTYPE.toDate(), DATE_TO_PROTOTYPE.toDate());
        DateRange secondDateRange = new DateRange(DATE_FROM_PROTOTYPE.toDate(), DATE_TO_PROTOTYPE.toDate());
        DateRange thirdDateRange = new DateRange(DATE_FROM_PROTOTYPE.plusDays(1).toDate(), DATE_TO_PROTOTYPE.plusDays(3).toDate());

        // when
        Set<DateRange> dateRangesSet = Sets.newHashSet(firstDateRange, secondDateRange, thirdDateRange);

        // then
        assertTrue(dateRangesSet.contains(firstDateRange));
        assertTrue(dateRangesSet.contains(secondDateRange));
        assertTrue(dateRangesSet.contains(thirdDateRange));

        assertEquals(2, dateRangesSet.size());

        assertEquals(firstDateRange.hashCode(), secondDateRange.hashCode());
        assertFalse(firstDateRange.hashCode() == thirdDateRange.hashCode());
        assertFalse(secondDateRange.hashCode() == thirdDateRange.hashCode());

        assertEquals(firstDateRange.hashCode(), firstDateRange.hashCode());
        assertEquals(secondDateRange.hashCode(), secondDateRange.hashCode());
        assertEquals(thirdDateRange.hashCode(), thirdDateRange.hashCode());

        assertEquals(firstDateRange, firstDateRange);
        assertEquals(secondDateRange, secondDateRange);
        assertEquals(thirdDateRange, thirdDateRange);

        assertEquals(firstDateRange, secondDateRange);
        assertEquals(secondDateRange, firstDateRange);

        assertFalse(secondDateRange.equals(thirdDateRange));
        assertFalse(thirdDateRange.equals(secondDateRange));

        assertFalse(thirdDateRange.equals(firstDateRange));
        assertFalse(firstDateRange.equals(thirdDateRange));
    }
}
