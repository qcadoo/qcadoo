package com.qcadoo.view.api.utils;

import org.springframework.stereotype.Service;

/**
 * Helper service for convert time from database to format (hh:mm:ss)
 * 
 * @since 0.4.0
 */
@Service
public class TimeConverterService {

    /**
     * Convert integer time value to string in format hh:mm:ss
     * 
     * @param duration
     *            time value from database
     * @return time value in format hh:mm:ss
     */

    public String convertTimeToString(final Integer duration) {

        long longValueFromDuration = duration.longValue();
        long hours = longValueFromDuration / 3600;
        long minutes = longValueFromDuration % 3600 / 60;
        long seconds = longValueFromDuration % 3600 % 60;
        Boolean minus = false;
        if (hours < 0) {
            minus = true;
            hours = -hours;
        }
        if (minutes < 0) {
            minus = true;
            minutes = -minutes;
        }
        if (seconds < 0) {
            minus = true;
            seconds = -seconds;
        }

        return (minus ? "-" : "") + (hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":"
                + (seconds < 10 ? "0" : "") + seconds;
    }

}
