package com.qcadoo.report.internal.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.TimeZone;

import net.sf.jasperreports.engine.util.FormatFactory;

public class ReportFormatFactory implements FormatFactory {

    @Override
    public DateFormat createDateFormat(String pattern, Locale locale, TimeZone timezone) {
        return DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
    }

    @Override
    public NumberFormat createNumberFormat(String pattern, Locale locale) {
        return ReportNumberFormat.getInstance(locale);
    }

}
