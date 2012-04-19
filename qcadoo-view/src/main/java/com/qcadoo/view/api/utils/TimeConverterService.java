/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.4
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.view.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.qcadoo.localization.api.utils.DateUtils;

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
        return duration2String(duration);
    }

    public static String duration2String(final Integer duration) {
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

    /**
     * Converts value from data field to Date
     * 
     * @param dateFromField
     *            value from view field
     * @return date value in format dd:mm:rrrr hh:mm
     */

    public Date getDateFromField(final Object dateFromField) {
        try {
            return new SimpleDateFormat(DateUtils.L_DATE_TIME_FORMAT, Locale.getDefault()).parse((String) dateFromField);
        } catch (ParseException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

}
