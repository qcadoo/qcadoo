/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.7
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
package com.qcadoo.model.internal;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public abstract class AbstractModelXmlConverter {

    public static enum FieldsTag {
        PRIORITY, INTEGER, STRING, TEXT, DECIMAL, DATETIME, DATE, BOOLEAN, BELONGSTO, HASMANY, TREE, ENUM, DICTIONARY, PASSWORD, FILE
    }

    public static enum HooksTag {
        ONVIEW, ONCREATE, ONUPDATE, ONSAVE, ONCOPY, VALIDATESWITH
    }

    public static enum OtherTag {
        IDENTIFIER
    }

    protected static enum FieldTag {
        VALIDATESLENGTH, VALIDATESPRECISION, VALIDATESSCALE, VALIDATESRANGE, VALIDATESWITH, VALIDATESREGEX
    }

    protected static final String TAG_MODEL = "model";

    protected static final String TAG_FIELDS = "fields";

    protected static final String TAG_HOOKS = "hooks";

    protected static final String TAG_PLUGIN = "plugin";

    protected String getPluginIdentifier(final XMLStreamReader reader) {
        return getStringAttribute(reader, "plugin");
    }

    protected String getIdentifierExpression(final XMLStreamReader reader) {
        return getStringAttribute(reader, "expression");
    }

    protected Integer getIntegerAttribute(final XMLStreamReader reader, final String name) {
        String stringValue = reader.getAttributeValue(null, name);
        if (stringValue != null) {
            return Integer.valueOf(stringValue);
        } else {
            return null;
        }
    }

    protected boolean getBooleanAttribute(final XMLStreamReader reader, final String name, final boolean defaultValue) {
        String stringValue = reader.getAttributeValue(null, name);
        if (stringValue != null) {
            return Boolean.valueOf(stringValue);
        } else {
            return defaultValue;
        }
    }

    protected String getStringAttribute(final XMLStreamReader reader, final String name) {
        return reader.getAttributeValue(null, name);
    }

    protected String getTagStarted(final XMLStreamReader reader) {
        if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
            return reader.getLocalName();
        } else {
            return null;
        }
    }

    protected boolean isTagStarted(final XMLStreamReader reader, final String tagName) {
        return (reader.getEventType() == XMLStreamConstants.START_ELEMENT && tagName.equals(reader.getLocalName()));
    }

    protected boolean isTagEnded(final XMLStreamReader reader, final String tagName) {
        return (reader.getEventType() == XMLStreamConstants.END_ELEMENT && tagName.equals(reader.getLocalName()));
    }

}
