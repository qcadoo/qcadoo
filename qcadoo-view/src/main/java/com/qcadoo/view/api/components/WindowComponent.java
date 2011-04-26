package com.qcadoo.view.api.components;

import com.qcadoo.view.api.ribbon.Ribbon;

/**
 * Represents window component
 * 
 * @since 0.4.0
 */
public interface WindowComponent {

    /**
     * Returns robbon of this window
     * 
     * @return robbon of this window
     */
    Ribbon getRibbon();

    /**
     * Informs that this window's ribbon should be updated
     */
    void requestRibbonRender();

}