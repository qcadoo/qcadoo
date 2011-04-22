package com.qcadoo.view.internal.menu.module;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.view.internal.api.InternalMenuService;

public class MenuCategoryModule extends Module {

    private final InternalMenuService menuService;

    private final String menuCategoryName;

    private final String pluginIdentifier;

    public MenuCategoryModule(final InternalMenuService menuService, final String pluginIdentifier, final String menuCategoryName) {
        this.menuService = menuService;
        this.pluginIdentifier = pluginIdentifier;
        this.menuCategoryName = menuCategoryName;
    }

    @Override
    public void multiTenantEnable() {
        menuService.createCategory(pluginIdentifier, menuCategoryName);
    }

    @Override
    public void multiTenantDisable() {
        menuService.removeCategory(pluginIdentifier, menuCategoryName);
    }

}
