package com.qcadoo.model.api;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;

public class BigDecimalUtilsTest {

    @Test
    public final void shouldValuesEqualsReturnCorrectValue() {
        Assert.assertTrue(BigDecimalUtils.valueEquals(null, null));
        Assert.assertFalse(BigDecimalUtils.valueEquals(null, BigDecimal.ZERO));
        Assert.assertFalse(BigDecimalUtils.valueEquals(BigDecimal.ZERO, null));
        Assert.assertFalse(BigDecimalUtils.valueEquals(BigDecimal.ONE, BigDecimal.ZERO));
        Assert.assertTrue(BigDecimalUtils.valueEquals(BigDecimal.ZERO.setScale(100), BigDecimal.ZERO.setScale(2)));
    }

    @Test
    public final void shouldConvertNullToZero() {
        // given
        BigDecimal notNullDecimal = BigDecimal.TEN;

        // when
        BigDecimal fromNullRes = BigDecimalUtils.convertNullToZero(null);
        BigDecimal fromNotNullRes = BigDecimalUtils.convertNullToZero(notNullDecimal);

        // then
        Assert.assertEquals(0, notNullDecimal.compareTo(fromNotNullRes));
        Assert.assertEquals(0, BigDecimal.ZERO.compareTo(fromNullRes));
    }

    @Test
    public final void shouldConvertNullToOne() {
        // given
        BigDecimal notNullDecimal = BigDecimal.TEN;

        // when
        BigDecimal fromNullRes = BigDecimalUtils.convertNullToOne(null);
        BigDecimal fromNotNullRes = BigDecimalUtils.convertNullToOne(notNullDecimal);

        // then
        Assert.assertEquals(0, notNullDecimal.compareTo(fromNotNullRes));
        Assert.assertEquals(0, BigDecimal.ONE.compareTo(fromNullRes));
    }

}
