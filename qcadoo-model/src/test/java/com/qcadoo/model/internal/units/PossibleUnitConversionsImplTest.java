package com.qcadoo.model.internal.units;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.math.MathContext;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.units.UnitConversion;

public class PossibleUnitConversionsImplTest {

    private static final String UNIT_FROM = "unitFrom";

    private PossibleUnitConversionsImpl possibleUnitConversionsImpl;

    @Mock
    private DataDefinition unitConversionItemDD;

    @Before
    public final void init() {
        MockitoAnnotations.initMocks(this);

        this.possibleUnitConversionsImpl = new PossibleUnitConversionsImpl(UNIT_FROM, MathContext.DECIMAL64, unitConversionItemDD);
    }

    private UnitConversion mockUnitConversion(final String unitFrom, final BigDecimal ratio, final String unitTo) {
        final UnitConversion unitConversion = mock(UnitConversion.class);
        given(unitConversion.getUnitFrom()).willReturn(unitFrom);
        given(unitConversion.getUnitTo()).willReturn(unitTo);
        given(unitConversion.getRatio()).willReturn(ratio);
        return unitConversion;
    }

    private void assertBigDecimalEquals(final BigDecimal expected, final BigDecimal actual) {
        if (expected.compareTo(actual) != 0) {
            Assert.fail("expected " + expected + " but actual value is " + actual);
        }
    }

    @Test
    public final void shouldThrowExceptionIfConversionDoesNotExists() {
        // when & then
        try {
            possibleUnitConversionsImpl.convertTo(BigDecimal.ONE, "kg");
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public final void shouldThrowExceptionWhenTryToInsertWrongConversion() {
        // given
        final String unitTo = "unitTo";
        final String unitFrom = "wrong" + UNIT_FROM;

        // when & then
        try {
            possibleUnitConversionsImpl.addConversion(mockUnitConversion(unitFrom, BigDecimal.ONE, unitTo));
            Assert.fail();
        } catch (Exception e) {
        }

    }

    @Test
    public final void shouldReturnConvertedValueIFConversionExists() {
        // given
        final String unitTo = "unitTo";
        possibleUnitConversionsImpl.addConversion(mockUnitConversion(UNIT_FROM, new BigDecimal("0.5"), unitTo));

        // when
        final BigDecimal result = possibleUnitConversionsImpl.convertTo(BigDecimal.valueOf(10L), unitTo);

        // then
        assertBigDecimalEquals(BigDecimal.valueOf(5L), result);

    }

}
