package com.qcadoo.model.internal;

import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.qcadoo.model.api.NumberService;

@Component
public class NumberServiceImpl implements NumberService {

    private static DecimalFormat decimalFormat = null;

    @Override
    public MathContext getMathContext() {
        return MathContext.DECIMAL64;
    }

    @Override
    public DecimalFormat getDecimalFormat(final Locale locale) {
        if (decimalFormat == null) {
            decimalFormat = (DecimalFormat) DecimalFormat.getInstance(locale);
            decimalFormat.setMaximumFractionDigits(3);
            decimalFormat.setMinimumFractionDigits(3);
        }
        return decimalFormat;
    }
}
