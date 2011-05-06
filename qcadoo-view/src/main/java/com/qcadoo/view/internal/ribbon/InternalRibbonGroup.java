package com.qcadoo.view.internal.ribbon;

import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.view.api.ribbon.RibbonGroup;

public interface InternalRibbonGroup extends RibbonGroup {

    /**
     * Add item to this group
     * 
     * @param item
     *            item to add
     */
    void addItem(final InternalRibbonActionItem item);

    /**
     * Generates JSON representation of this ribbon group
     * 
     * @return JSON representation of this ribbon group
     * @throws JSONException
     */
    JSONObject getAsJson() throws JSONException;

    /**
     * Gets copy of this group - internal usage only
     * 
     * @return copy of this group
     */
    InternalRibbonGroup getCopy();

    /**
     * Gets ribbon group with only updated items - internal usage only
     * 
     * @return ribon group with only updated fields
     */
    InternalRibbonGroup getUpdate();

    /**
     * Returns identifier of plugin that created this group or null when no such plugin identifier defined
     * 
     * @return identifier of plugin that created this group or null when no such plugin identifier defined
     */
    String getExtensionPluginIdentifier();

    /**
     * Sets identifier of plugin that created this group - internal usage only
     * 
     * @param extensionPluginIdentifier
     *            identifier of plugin that created this group
     */
    void setExtensionPluginIdentifier(String extensionPluginIdentifier);
}
