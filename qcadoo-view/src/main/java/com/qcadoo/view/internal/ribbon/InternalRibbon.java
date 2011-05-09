package com.qcadoo.view.internal.ribbon;

import org.json.JSONObject;

import com.qcadoo.view.api.ribbon.Ribbon;

public interface InternalRibbon extends Ribbon {

    /**
     * Set identifier of this ribbon
     * 
     * @param name
     *            identifier of this ribbon
     */
    void setName(String name);

    /**
     * Add group to this ribbon
     * 
     * @param group
     *            group to add
     */
    void addGroup(InternalRibbonGroup group);

    /**
     * Removes group from this ribbon
     * 
     * @param group
     *            group to remove
     */
    void removeGroup(InternalRibbonGroup group);

    /**
     * generates JSON string that contains all ribbon definition
     * 
     * @return JSON ribbon definition
     */
    JSONObject getAsJson();

    /**
     * Gets copy of this robbon - internal usage only
     * 
     * @return copy of this ribbon
     */
    InternalRibbon getCopy();

    /**
     * Gets ribbon with only updated fields - internal usage only
     * 
     * @return ribon with only updated fields
     */
    InternalRibbon getUpdate();
}
