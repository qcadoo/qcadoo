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

}
