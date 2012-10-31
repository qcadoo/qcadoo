/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.2.0-SNAPSHOT
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
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
