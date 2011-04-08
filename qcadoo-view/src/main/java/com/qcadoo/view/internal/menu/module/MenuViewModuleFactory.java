package com.qcadoo.view.internal.menu.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
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

        Preconditions.checkNotNull(menuName, "Missing name attribute of menu-item module");
        Preconditions.checkNotNull(menuCategory, "Missing category attribute of menu-item module");
        Preconditions.checkNotNull(menuViewName, "Missing view attribute of menu-item module");

        return new MenuModule(menuService, pluginIdentifier, menuName, menuCategory, pluginIdentifier, menuViewName, null);
    }

    @Override
    public String getIdentifier() {
        return "menu-item";
    }

}
