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
package com.qcadoo.view.internal.api;

/**
 * Represents custom event which can be added to every {@link com.qcadoo.view.internal.api.ComponentPattern}.
 * 
 * @since 0.4.0
 */
public final class ComponentCustomEvent {

    private final String event;

    private final Object object;

    private final String method;

    private final String pluginIdentifier;

    /**
     * Basic constructor
     * 
     * @param event
     *            event name
     * @param object
     *            object with method to call
     * @param method
     *            name of method to call
     * @param pluginIdentifier
     *            identifier of plugin with add this custom event (can be null)
     */
    public ComponentCustomEvent(final String event, final Object object, final String method, final String pluginIdentifier) {
        this.event = event;
        this.object = object;
        this.method = method;
        this.pluginIdentifier = pluginIdentifier;
    }

    /**
     * Returns name of method to call
     * 
     * @return name of method to call
     */
    public String getMethod() {
        return method;
    }

    /**
     * Returns object with method to call
     * 
     * @return object with method to call
     */
    public Object getObject() {
        return object;
    }

    /**
     * Returns event name
     * 
     * @return event name
     */
    public String getEvent() {
        return event;
    }

    /**
     * Returns identifier of plugin with add this custom event or null if no such identifier specified
     * 
     * @return identifier of plugin with add this custom event
     */
    public String getPluginIdentifier() {
        return pluginIdentifier;
    }

}
