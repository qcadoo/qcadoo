/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.0
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

package com.qcadoo.model.api.expression;

import java.util.List;
import java.util.Locale;

import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.internal.ExpressionServiceImpl;

public final class ExpressionUtils {

    private ExpressionUtils() {
    }

    public static String getValue(final Entity entity, final List<FieldDefinition> fieldDefinitions, final Locale locale) {
        return ExpressionServiceImpl.getInstance().getValue(entity, fieldDefinitions, locale);
    }

    public static String getValue(final Entity entity, final String expression, final Locale locale) {
        return ExpressionServiceImpl.getInstance().getValue(entity, expression, locale);
    }

}
