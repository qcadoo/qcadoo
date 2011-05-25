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
