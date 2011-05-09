package com.qcadoo.view.internal.menu.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.view.internal.api.InternalMenuService;

public class MenuCategoryModuleFactory extends ModuleFactory<MenuCategoryModule> {

    @Autowired
    private InternalMenuService menuService;

    @Override
    public MenuCategoryModule parse(final String pluginIdentifier, final Element element) {
        String menuCategoryName = getRequiredAttribute(element, "name");

        return new MenuCategoryModule(menuService, pluginIdentifier, menuCategoryName);
    }

    @Override
    public String getIdentifier() {
        return "menu-category";
    }

}
