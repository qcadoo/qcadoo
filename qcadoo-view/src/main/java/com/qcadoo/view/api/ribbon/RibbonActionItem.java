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

package com.qcadoo.view.api.ribbon;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents single ribbon item
 * 
 * @since 0.4.0
 */
public class RibbonActionItem {

    /**
     * Type of ribbon item
     */
    public static enum Type {
        /**
         * simple big button
         */
        BIG_BUTTON,
        /**
         * simple small button
         */
        SMALL_BUTTON,
        /**
         * checkbox
         */
        CHECKBOX,
        /**
         * combobox
         */
        COMBOBOX,
        /**
         * small empty space
         */
        SMALL_EMPTY_SPACE
    }

    private String name;

    private Type type;

    private String icon;

    private String script;

    private String clickAction;

    private Boolean enabled;

    private String message;

    private boolean shouldBeUpdated = false;

    /**
     * Get defined item click action
     * 
     * @return defined item click action
     */
    public final String getAction() {
        return clickAction;
    }

    /**
     * Set defined item action
     * 
     * @param clickAction
     *            defined item action
     */
    public final void setAction(final String clickAction) {
        this.clickAction = clickAction;
    }

    /**
     * Get identifier of this ribbon item
     * 
     * @return identifier of this ribbon item
     */
    public final String getName() {
        return name;
    }

    /**
     * Set identifier of this ribbon item
     * 
     * @param name
     *            identifier of this ribbon item
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * Get item type
     * 
     * @return item type
     */
    public final Type getType() {
        return type;
    }

    /**
     * Set item type
     * 
     * @param type
     *            item type
     */
    public final void setType(final Type type) {
        this.type = type;
    }

    /**
     * Get item icon (null if item without icon)
     * 
     * @return item icon
     */
    public final String getIcon() {
        return icon;
    }

    /**
     * Set item icon (null if item without icon)
     * 
     * @param icon
     *            item icon
     */
    public final void setIcon(final String icon) {
        this.icon = icon;
    }

    /**
     * Generates JSON representation of this ribbon item
     * 
     * @return JSON representation of this ribbon item
     * @throws JSONException
     */
    public JSONObject getAsJson() throws JSONException {
        JSONObject itemObject = new JSONObject();
        itemObject.put("name", name);
        itemObject.put("type", type);
        itemObject.put("icon", icon);
        itemObject.put("enabled", enabled);
        itemObject.put("message", message);
        if (script != null) {
            itemObject.put("script", script);
        }
        itemObject.put("clickAction", clickAction);
        return itemObject;
    }

    /**
     * Returns script of this ribbon item
     * 
     * @return script of this ribbon item
     */
    public String getScript() {
        return script;
    }

    /**
     * Sets script of this ribbon item
     * 
     * @param script
     *            script of this ribbon item
     */
    public void setScript(String script) {
        this.script = script;
    }

    /**
     * Returns true if this item is enabled
     * 
     * @return true if this item is enabled
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets this item state
     * 
     * @param enabled
     *            true when this item should be enabled or false when this item should be disabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns message connected to this item
     * 
     * @return message connected to this item
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets message connected to this item
     * 
     * @param message
     *            new message connected to this item
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns copy of this item - internal usage only
     * 
     * @return copy of this item
     */
    public RibbonActionItem getCopy() {
        RibbonActionItem copy = new RibbonActionItem();
        copyFields(copy);
        return copy;
    }

    protected void copyFields(RibbonActionItem item) {
        item.setName(name);
        item.setType(type);
        item.setIcon(icon);
        item.setScript(script);
        item.setAction(clickAction);
        item.setEnabled(enabled);
        item.setMessage(message);
    }

    /**
     * Returns information if this item state should be updated
     * 
     * @return information if this item state should be updated
     */
    public boolean isShouldBeUpdated() {
        return shouldBeUpdated;
    }

    /**
     * Informs that this item state should be updated
     * 
     * @param shouldBeUpdated
     *            true if this item state should be updated
     */
    public void requestUpdate(boolean shouldBeUpdated) {
        this.shouldBeUpdated = shouldBeUpdated;
    }
}
