package com.qcadoo.view.internal.ribbon;

import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.view.api.ribbon.RibbonActionItem;

public interface InternalRibbonActionItem extends RibbonActionItem {

    /**
     * Set defined item action
     * 
     * @param clickAction
     *            defined item action
     */
    void setAction(String clickAction);

    /**
     * Set identifier of this ribbon item
     * 
     * @param name
     *            identifier of this ribbon item
     */
    void setName(String name);

    /**
     * Set item type
     * 
     * @param type
     *            item type
     */
    void setType(Type type);

    /**
     * Generates JSON representation of this ribbon item
     * 
     * @return JSON representation of this ribbon item
     * @throws JSONException
     */
    JSONObject getAsJson() throws JSONException;

    /**
     * Sets script of this ribbon item
     * 
     * @param script
     *            script of this ribbon item
     */
    void setScript(String script);

    /**
     * Returns copy of this item - internal usage only
     * 
     * @return copy of this item
     */
    InternalRibbonActionItem getCopy();

    /**
     * Returns information if this item state should be updated
     * 
     * @return information if this item state should be updated
     */
    boolean isShouldBeUpdated();
}
