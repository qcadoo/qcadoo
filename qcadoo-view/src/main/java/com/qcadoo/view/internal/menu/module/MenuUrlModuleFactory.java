package com.qcadoo.view.internal.menu.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.view.internal.api.InternalMenuService;

public class MenuUrlModuleFactory implements ModuleFactory<MenuModule> {

    @Autowired
    private InternalMenuService menuService;

    @Override
    public void init() {
        // empty
    }

    @Override
    public MenuModule parse(final String pluginIdentifier, final Element element) {
        String menuName = element.getAttributeValue("name");
        String menuCategory = element.getAttributeValue("category");
        String menuUrl = element.getAttributeValue("url");

        if (menuName == null) {
            throw new IllegalStateException("Missing name attribute of menu-item-url module");
        }

        if (menuCategory == null) {
            throw new IllegalStateException("Missing category attribute of menu-item-url module");
        }

        if (menuUrl == null) {
            throw new IllegalStateException("Missing url attribute of menu-item-url module");
        }

        return new MenuModule(menuService, pluginIdentifier, menuName, menuCategory, null, null, menuUrl);
    }

    @Override
    public String getIdentifier() {
        return "menu-item-url";
    }

}
