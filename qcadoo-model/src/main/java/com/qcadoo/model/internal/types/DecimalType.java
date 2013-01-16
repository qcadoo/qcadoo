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
package com.qcadoo.model.internal.types;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.types.FieldType;
import com.qcadoo.model.internal.api.ValueAndError;

public final class DecimalType implements FieldType {

    @Override
    public Class<?> getType() {
        return BigDecimal.class;
    }

    @Override
    public ValueAndError toObject(final FieldDefinition fieldDefinition, final Object value) {
        BigDecimal decimal = null;

        if (value instanceof BigDecimal) {
            decimal = (BigDecimal) value;
        } else {
            try {
                decimal = new BigDecimal(String.valueOf(value));
            } catch (NumberFormatException e) {
                return ValueAndError.withError("qcadooView.validate.field.error.invalidNumericFormat");
            }
        }
        return ValueAndError.withoutError(decimal);
    }

    @Override
    public String toString(final Object value, final Locale locale) {
        NumberFormat format = null;
        if (locale == null) {
            format = NumberFormat.getNumberInstance();
        } else {
            format = NumberFormat.getNumberInstance(locale);
        }
        format.setMaximumFractionDigits(5);
        return format.format(value);
    }

    @Override
    public Object fromString(final String value, final Locale locale) {
        ParsePosition parsePosition = new ParsePosition(0);
        String trimedValue = value.replaceAll(" ", "");
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        formatter.setParseBigDecimal(true);
        Object parsedValue = formatter.parseObject(trimedValue, parsePosition);

        if (parsePosition.getIndex() == trimedValue.length()) {
            return parsedValue;
        }

        return value;
    }

}
