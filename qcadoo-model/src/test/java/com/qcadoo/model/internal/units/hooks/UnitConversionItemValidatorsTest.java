package com.qcadoo.model.internal.units.hooks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.constants.UnitConversionItemFields;

public class UnitConversionItemValidatorsTest {

    private UnitConversionItemValidators unitConversionItemValidators;

    @Mock
    private Entity unitConversionItem;

    @Mock
    private DataDefinition dataDefinition;

    @Before
    public final void init() {
        MockitoAnnotations.initMocks(this);

        unitConversionItemValidators = new UnitConversionItemValidators();
    }

    private void stubConversionQuantityFrom(final BigDecimal value) {
        given(unitConversionItem.getDecimalField(UnitConversionItemFields.QUANTITY_FROM)).willReturn(value);
    }

    @Test
    public final void shouldValidateQuantityFromAndReturnTrue() {
        // given
        stubConversionQuantityFrom(BigDecimal.ONE);

        // when
        final boolean isValid = unitConversionItemValidators.validateQuantityFrom(dataDefinition, unitConversionItem);

        // then
        assertTrue(isValid);
    }

    @Test
    public final void shouldValidateQuantityFromAndReturnFalse() {
        // given
        stubConversionQuantityFrom(BigDecimal.ZERO);

        // when
        final boolean isValid = unitConversionItemValidators.validateQuantityFrom(dataDefinition, unitConversionItem);

        // then
        assertFalse(isValid);
    }

    @Test
    public final void shouldValidateQuantityFromAndReturnFalseIfQuantityIsNotDefined() {
        // given
        stubConversionQuantityFrom(null);

        // when
        final boolean isValid = unitConversionItemValidators.validateQuantityFrom(dataDefinition, unitConversionItem);

        // then
        assertFalse(isValid);
    }
}
