package com.qcadoo.testing.model;

import java.math.BigDecimal;
import java.math.MathContext;

import com.qcadoo.model.api.NumberService;
import com.qcadoo.model.internal.NumberServiceImpl;

public final class NumberServiceMock {

    private NumberServiceMock() {
    }

    public static NumberService ignoringScale() {
        return new NumberServiceMockImpl(true);
    }

    public static NumberService scaleAware() {
        return new NumberServiceMockImpl(false);
    }

    private static final class NumberServiceMockImpl implements NumberService {

        private final boolean ignoreScale;

        private final NumberService numberService = new NumberServiceImpl();

        private NumberServiceMockImpl(final boolean ignoreScale) {
            this.ignoreScale = ignoreScale;
        }

        @Override
        public MathContext getMathContext() {
            return numberService.getMathContext();
        }

        @Override
        public String format(final Object obj) {
            return numberService.format(obj);
        }

        @Override
        public BigDecimal setScale(final BigDecimal decimal) {
            if (ignoreScale) {
                return decimal;
            }
            return numberService.setScale(decimal);
        }

        @Override
        public BigDecimal setScale(final BigDecimal decimal, final int newScale) {
            if (ignoreScale) {
                return decimal;
            }
            return numberService.setScale(decimal, newScale);
        }

        @Override
        public String formatWithMinimumFractionDigits(final Object obj, final int minimumFractionDigits) {
            return numberService.formatWithMinimumFractionDigits(obj, minimumFractionDigits);
        }
    }
}
