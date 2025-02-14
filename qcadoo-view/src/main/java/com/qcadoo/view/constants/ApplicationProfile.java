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
package com.qcadoo.view.constants;

import org.apache.commons.lang3.StringUtils;

public enum ApplicationProfile {

    MES("mes-logo"), APS( "aps-logo"), MES_APS( "mes-aps-logo"), WMS( "wms-logo"), TEST( "test-logo");

    private final String logo;

    ApplicationProfile(final String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public static ApplicationProfile parseString(final String rawStringValue) {
        for (ApplicationProfile applicationProfile : values()) {
            if (StringUtils.equalsIgnoreCase(rawStringValue, applicationProfile.toString())) {
                return applicationProfile;
            }
        }
        throw new IllegalArgumentException(String.format("Cannot parse ApplicationProfile from '%s'", rawStringValue));
    }
}
