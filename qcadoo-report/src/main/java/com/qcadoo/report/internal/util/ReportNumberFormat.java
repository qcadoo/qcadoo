/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.4
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
package com.qcadoo.report.internal.util;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ReportNumberFormat extends NumberFormat {

    private static final long serialVersionUID = 8881156984775289396L;

    private NumberFormat decimalNumberFormat;

    private NumberFormat integerNumberFormat;

    private static final Map<Locale, ReportNumberFormat> formatters = new HashMap<Locale, ReportNumberFormat>();

    public static ReportNumberFormat getInstance(final Locale locale) {
        ReportNumberFormat format = formatters.get(locale);
        if (format == null) {
            synchronized (formatters) {
                format = formatters.get(locale);
                if (format == null) {
                    format = new ReportNumberFormat(locale);
                    formatters.put(locale, format);
                }
            }
        }
        return format;
    }

    private ReportNumberFormat(Locale locale) {
        decimalNumberFormat = NumberFormat.getNumberInstance(locale);
        decimalNumberFormat.setMinimumFractionDigits(3);
        integerNumberFormat = NumberFormat.getNumberInstance(locale);
    }

    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        return decimalNumberFormat.format(number, toAppendTo, pos);
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        return integerNumberFormat.format(number, toAppendTo, pos);
    }

    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        return decimalNumberFormat.parse(source, parsePosition);
    }
}
