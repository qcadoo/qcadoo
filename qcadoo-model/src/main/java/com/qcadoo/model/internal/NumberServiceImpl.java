package com.qcadoo.model.internal;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

import java.math.MathContext;
import java.text.DecimalFormat;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.qcadoo.model.api.NumberService;

@Component
public class NumberServiceImpl implements NumberService {

    private static DecimalFormat decimalFormat = null;

    @PostConstruct
    public void init() {
        decimalFormat = (DecimalFormat) DecimalFormat.getInstance(getLocale());
        decimalFormat.setMaximumFractionDigits(3);
        decimalFormat.setMinimumFractionDigits(3);
    }

    @Override
    public MathContext getMathContext() {
        return MathContext.DECIMAL64;
    }

    @Override
    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }
}
