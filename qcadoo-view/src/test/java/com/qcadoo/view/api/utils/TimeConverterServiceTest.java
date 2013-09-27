package com.qcadoo.view.api.utils;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTimeConstants;
import org.junit.Test;

public class TimeConverterServiceTest {

    @Test
    public final void shouldConvertDurationToString() {
        // given
        Integer shortDuration = DateTimeConstants.SECONDS_PER_HOUR + DateTimeConstants.SECONDS_PER_MINUTE + 1;
        Integer longDuration = 2 * DateTimeConstants.SECONDS_PER_DAY + shortDuration;

        // when
        String shortDurationStringVal = TimeConverterService.durationToString(shortDuration);
        String longDurationStringVal = TimeConverterService.durationToString(longDuration);

        // then
        assertEquals("01:01:01", shortDurationStringVal);
        assertEquals("49:01:01", longDurationStringVal);
    }

    @Test
    public final void shouldReturnErrorStringValueIfDurationIsNull() {
        // when
        String strVal = TimeConverterService.durationToString(null);

        // then
        assertEquals("###", strVal);
    }

}
