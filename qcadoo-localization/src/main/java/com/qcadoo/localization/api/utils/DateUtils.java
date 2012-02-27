/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.3
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
package com.qcadoo.localization.api.utils;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility for date localization.
 * 
 * @since 0.4.0
 */
public final class DateUtils {

    private DateUtils() {
    }

    /**
     * Date format.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Date-time format.
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String REPORT_DATE_TIME_FORMAT = "yyyy_MM_dd_HH_mm_ss";

    /**
     * Date-time format for filenames.
     */
    public static final SimpleDateFormat REPORT_D_T_F = new SimpleDateFormat(REPORT_DATE_TIME_FORMAT, getLocale());

    /**
     * Parse string into date, with autocomplete missing month and day.
     * 
     * Examples with up-complete:
     * 
     * <ul>
     * <li>2010: 2010-12-31</li>
     * <li>2010-03: 2010-03-31</li>
     * <li>2010-03-06: 2010-03-06</li>
     * </ul>
     * 
     * Examples with down-complete:
     * 
     * <ul>
     * <li>2010: 2010-01-01</li>
     * <li>2010-03: 2010-03-01</li>
     * <li>2010-03-06: 2010-03-06</li>
     * </ul>
     * 
     * @param dateExpression
     *            string with date expression
     * @param upComplete
     *            true if up-complete, otherwise down-complete
     * @return date or null if year is lower than 1500
     * @throws ParseException
     *             if year, month or day is invalid
     */
    public static Date parseAndComplete(final String dateExpression, final boolean upComplete) throws ParseException {
        String[] dateExpressionParts = dateExpression.split("-");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        boolean dayDefined = false;

        try {
            int year = Integer.parseInt(dateExpressionParts[0]);
            if (year > 2500) {
                throw new ParseException("wrong date", 1);
            }
            if (year < 1500) {
                throw new ParseException("wrong date", 1);
            }
            cal.set(Calendar.YEAR, year);

            if (dateExpressionParts.length > 1) {
                int month = Integer.parseInt(dateExpressionParts[1]);
                if (month > 12 || month < 1) {
                    throw new ParseException("wrong date", 1);
                }
                cal.set(Calendar.MONTH, month - 1);
            } else {
                if (upComplete) {
                    cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
                } else {
                    cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
                }
            }

            if (dateExpressionParts.length > 2) {
                int day = Integer.parseInt(dateExpressionParts[2]);
                if (day > 0) {
                    cal.set(Calendar.DAY_OF_MONTH, day);
                    dayDefined = true;
                }
            }
            if (!dayDefined) {
                if (upComplete) {
                    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                } else {
                    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                }
            }
            return cal.getTime();
        } catch (NumberFormatException e) {
            throw (ParseException) new ParseException("wrong date", 1).initCause(e);
        }
    }

}
