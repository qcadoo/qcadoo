package com.qcadoo.model.internal;

import static java.math.RoundingMode.HALF_EVEN;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

import java.math.BigDecimal;
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
        initialise();
    }

    private static void initialise() {
        decimalFormat = (DecimalFormat) DecimalFormat.getInstance(getLocale());
        decimalFormat.setMaximumFractionDigits(3);
        decimalFormat.setMinimumFractionDigits(3);
        decimalFormat.setRoundingMode(HALF_EVEN);
    }

    @Override
    public MathContext getMathContext() {
        return MathContext.DECIMAL64;
    }

    @Override
    public String format(Object obj) {
        return decimalFormat.format(obj);
    }

    @Override
    public BigDecimal setScale(BigDecimal decimal) {
        return decimal.setScale(3, HALF_EVEN);
    }
}
