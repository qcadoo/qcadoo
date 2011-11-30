/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.0
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
package com.qcadoo.model.internal.api;

public final class ValueAndError {

    private final Object value;

    private final String message;

    private final String[] args;

    private ValueAndError(final Object value, final String message, final String... args) {
        this.value = value;
        this.message = message;
        this.args = args;
    }

    public static ValueAndError withoutError(final Object value) {
        return new ValueAndError(value, null);
    }

    public static ValueAndError empty() {
        return new ValueAndError(null, null);
    }

    public static ValueAndError withError(final String message, final String... args) {
        return new ValueAndError(null, message, args);
    }

    public boolean isValid() {
        return message == null;
    }

    public Object getValue() {
        return value;
    }

    public String[] getArgs() {
        return args;
    }

    public String getMessage() {
        return message;
    }

}
