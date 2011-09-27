/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.8
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
package com.qcadoo.view.internal.menu;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents single menu item
 * 
 * @since 0.4.0
 */
public abstract class MenuItem {

    private final String name;

    private final String label;

    private final String pluginIdentifier;

    /**
     * @param name
     *            identifier of item
     * @param label
     *            item label to display
     * @param pluginIdentifier
     *            plugin identifier of this item
     */
    public MenuItem(final String name, final String label, final String pluginIdentifier) {
        this.name = name;
        this.label = label;
        this.pluginIdentifier = pluginIdentifier;
    }

    /**
     * Get identifier of item
     * 
     * @return identifier of item
     */
    public final String getName() {
        return name;
    }

    /**
     * Get item label to display
     * 
     * @return item label to display
     */
    public final String getLabel() {
        return label;
    }

    /**
     * Get plugin identifier of this item
     * 
     * @return plugin identifier of this item
     */
    public final String getPluginIdentifier() {
        return pluginIdentifier;
    }

    /**
     * Get URL that this item leads to
     * 
     * @return URL that this item leads to
     */
    public abstract String getPage();

    /**
     * Generates JSON representation of this item
     * 
     * @return JSON representation of this item
     * @throws JSONException
     */
    public final JSONObject getAsJson() throws JSONException {
        JSONObject itemObject = new JSONObject();
        itemObject.put("name", getName());
        itemObject.put("label", getLabel());
        itemObject.put("page", getPage());
        return itemObject;
    }

}
