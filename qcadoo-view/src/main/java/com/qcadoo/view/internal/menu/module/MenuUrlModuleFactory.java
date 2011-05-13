package com.qcadoo.view.internal.menu.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.view.internal.api.InternalMenuService;

public class MenuUrlModuleFactory extends ModuleFactory<MenuModule> {

    @Autowired
    private InternalMenuService menuService;

    @Override
    protected MenuModule parseElement(final String pluginIdentifier, final Element element) {
        String menuName = getRequiredAttribute(element, "name");
        String menuCategory = getRequiredAttribute(element, "category");
        String menuUrl = getRequiredAttribute(element, "url");

        return new MenuModule(getIdentifier(), menuService, pluginIdentifier, menuName, menuCategory, null, null, menuUrl);
    }

    @Override
    public String getIdentifier() {
        return "menu-item-url";
    }

}
