/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.2.0
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

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.constants.UnitConversionItemFields;

@Service
public class UnitConversionItemValidators {

    public boolean validateQuantityFrom(final DataDefinition dataDefinition, final Entity unitConversionItem) {
        final BigDecimal quantityFrom = unitConversionItem.getDecimalField(UnitConversionItemFields.QUANTITY_FROM);
        if (quantityFrom != null && BigDecimal.ONE.compareTo(quantityFrom) == 0) {
            return true;
        }

        final String errorMsg = "qcadooUnitConversions.unitConversionItem.validateError.quantityFrom.differentFromOne";
        unitConversionItem.addError(dataDefinition.getField(UnitConversionItemFields.QUANTITY_FROM), errorMsg);
        return false;
    }

    public boolean validateUnits(final DataDefinition dataDefinition, final Entity unitConversionItem) {
        String unitFrom = unitConversionItem.getStringField(UnitConversionItemFields.UNIT_FROM);
        String unitTo = unitConversionItem.getStringField(UnitConversionItemFields.UNIT_TO);
        if (unitFrom.equals(unitTo)) {
            unitConversionItem.addError(dataDefinition.getField(UnitConversionItemFields.UNIT_TO),
                    "qcadooUnitConversions.unitConversionItem.validateError.theSameUnit");
            return false;
        }
        return true;
    }

}
