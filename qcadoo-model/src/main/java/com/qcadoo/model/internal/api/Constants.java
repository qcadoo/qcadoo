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
package com.qcadoo.model.internal.api;

import org.springframework.core.io.support.ResourcePatternResolver;

public interface Constants {

    String XSL = "com/qcadoo/model/model.xsl";

    String XSD = "com/qcadoo/model/model.xsd";

    String RESOURCE_PATTERN = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "model/*.xml";

    String VALIDATION_MESSAGE_REQUIRED = "required";

    String VALIDATION_MESSAGE_BELOW_RANGE = "below range";

    String VALIDATION_MESSAGE_ABOVE_RANGE = "above range";

    String VALIDATION_MESSAGE_INVALID_LENGTH = "invalid length";

    String VALIDATION_MESSAGE_BELOW_MIN_LENGTH = "below min length";

    String VALIDATION_MESSAGE_ABOVE_MAX_LENGTH = "above max length";

    String VALIDATION_MESSAGE_INVALID_PRECISION = "invalid precision";

    String VALIDATION_MESSAGE_BELOW_MIN_PRECISION = "below min presicion";

    String VALIDATION_MESSAGE_ABOVE_MAX_PRECISION = "above max precision";

    String VALIDATION_MESSAGE_INVALID_SCALE = "invalid scale";

    String VALIDATION_MESSAGE_BELOW_MIN_SCALE = "below min scale";

    String VALIDATION_MESSAGE_ABOVE_MAX_SCALE = "above max scale";

}
