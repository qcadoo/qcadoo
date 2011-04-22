package com.qcadoo.view.api.components;

import com.qcadoo.view.api.ribbon.Ribbon;

public interface WindowComponent {

    Ribbon getRibbon();

    void requestRibbonRender();

}