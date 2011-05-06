package com.qcadoo.view.internal.ribbon;

import com.qcadoo.view.api.ribbon.RibbonComboItem;

public interface InternalRibbonComboItem extends RibbonComboItem {

    /**
     * Add dropdown item
     * 
     * @param item
     *            dropdown item
     */
    void addItem(InternalRibbonActionItem item);
}
