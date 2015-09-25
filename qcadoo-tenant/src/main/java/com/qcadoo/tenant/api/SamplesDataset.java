/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.4
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
package com.qcadoo.tenant.api;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents sample data set generation strategies
 * 
 * @since 1.1.6
 */
public enum SamplesDataset {

    /**
     * Do not generate any samples data, leave database untouched
     */
    NONE("none"),

    /**
     * Generate minimal samples data
     */
    MINIMAL("minimal"),

    /**
     * Generate test samples data
     */
    TEST("test"),

    /**
     * Generate randomized samples data
     */
    GENERATED("generated");

    private String stringValue;

    private SamplesDataset(final String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     * Getter for string representation
     * 
     * @return string representation
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * Parse {@link SamplesDataset} from string
     * 
     * @param string
     *            to parse
     * @return appropriate {@link SamplesDataset}
     * @throws IllegalArgumentException
     *             if there is no {@link SamplesDataset} matching to given string
     */
    public static final SamplesDataset parseString(final String string) {
        if (StringUtils.isBlank(string) || "none".equalsIgnoreCase(string)) {
            return NONE;
        } else if ("minimal".equalsIgnoreCase(string)) {
            return MINIMAL;
        } else if ("test".equalsIgnoreCase(string)) {
            return TEST;
        } else if ("generated".equalsIgnoreCase(string)) {
            return GENERATED;
        }
        throw new IllegalArgumentException("Wrong samples dataset: " + string);
    }
}
