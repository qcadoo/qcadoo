package com.qcadoo.view.internal.menu.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.view.internal.api.InternalMenuService;

public class MenuViewModuleFactory extends ModuleFactory<MenuModule> {

    @Autowired
    private InternalMenuService menuService;

    @Override
    public MenuModule parse(final String pluginIdentifier, final Element element) {
        String menuName = element.getAttributeValue("name");
        String menuCategory = element.getAttributeValue("category");
        String menuViewName = element.getAttributeValue("view");

        if (menuName == null) {
            throw new IllegalStateException("Missing name attribute of menu-item module");
        }

        if (menuCategory == null) {
            throw new IllegalStateException("Missing category attribute of menu-item module");
        }

        if (menuViewName == null) {
            throw new IllegalStateException("Missing view attribute of menu-item module");
        }

        return new MenuModule(menuService, pluginIdentifier, menuName, menuCategory, pluginIdentifier, menuViewName, null);
    }

    @Override
    public String getIdentifier() {
        return "menu-item";
    }

}
