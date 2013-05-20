package com.qcadoo.model.internal.types;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.qcadoo.model.api.NumberService;

public class DecimalTypeTest {

    private DecimalType decimalType;

    private static final Locale locale = Locale.getDefault();

    private NumberFormat numberFormat;

    @Before
    public final void init() {
        MockitoAnnotations.initMocks(this);

        decimalType = new DecimalType();
        numberFormat = NumberFormat.getNumberInstance(locale);
        numberFormat.setMaximumFractionDigits(Integer.MAX_VALUE);
        numberFormat.setMaximumIntegerDigits(Integer.MAX_VALUE);
    }

    @Test
    public final void shouldFormatNullAsEmptyString() {
        // when
        String result = decimalType.toString(null, locale);

        // then
        assertEquals("", result);
    }

    @Test
    public final void shouldFormatDecimalWithoutOfficiousTrimmingFractionalValue() {
        // given
        final String decimalAsString = "1234567."
                + StringUtils.repeat("9", NumberService.DEFAULT_MAX_FRACTION_DIGITS_IN_DECIMAL + 3);
        BigDecimal value = new BigDecimal(decimalAsString);

        // when
        String result = decimalType.toString(value, locale);

        // then
        assertFormattedEquals(value, result);
    }

    @Test
    public final void shouldFormatDecimalWithoutOfficiousTrimmingFractionalValue2() {
        // given
        final String decimalAsString = "1234567." + StringUtils.repeat("9", NumberService.DEFAULT_MAX_FRACTION_DIGITS_IN_DECIMAL)
                + "000001";
        BigDecimal value = new BigDecimal(decimalAsString);

        // when
        String result = decimalType.toString(value, locale);

        // then
        assertFormattedEquals(value, result);
    }

    @Test
    public final void shouldFormatDecimalTrimTrailingZeroes() {
        // given
        final String decimalAsString = "1234567." + StringUtils.repeat("9", 3);
        BigDecimal value = new BigDecimal(decimalAsString + "00000");

        // when
        String result = decimalType.toString(value, locale);

        // then
        assertFormattedEquals(value, result);
    }

    @Test
    public final void shouldFormatDecimalTrimTrailingZeroes2() {
        // given
        BigDecimal value = BigDecimal.ZERO.setScale(10);

        // when
        String result = decimalType.toString(value, locale);

        // then
        assertEquals("0", result);
    }

    private void assertFormattedEquals(final Object value, final String result) {
        assertEquals(numberFormat.format(value), result);
    }

}
