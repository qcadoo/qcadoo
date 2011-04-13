/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo MES
 * Version: 0.3.0
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

package com.qcadoo.view.internal.internal;

public final class ComponentCustomEvent {

    private final String event;

    private final Object object;

    private final String method;

    private final String pluginIdentifier;

    public ComponentCustomEvent(final String event, final Object object, final String method, final String pluginIdentifier) {
        this.event = event;
        this.object = object;
        this.method = method;
        this.pluginIdentifier = pluginIdentifier;
    }

    public String getMethod() {
        return method;
    }

    public Object getObject() {
        return object;
    }

    public String getEvent() {
        return event;
    }

    public String getPluginIdentifier() {
        return pluginIdentifier;
    }

}
