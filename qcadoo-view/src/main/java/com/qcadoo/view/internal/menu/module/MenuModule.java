package com.qcadoo.view.internal.menu.module;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.view.internal.api.InternalMenuService;

public class MenuModule extends Module {

    private final InternalMenuService menuService;

    private final String menuName;

    private final String menuCategory;

    private final String menuUrl;

    private final String pluginIdentifier;

    private final String menuViewPluginIdentifier;

    private final String menuViewName;

    public MenuModule(final InternalMenuService menuService, final String pluginIdentifier, final String menuName,
            final String menuCategory, final String menuViewPluginIdentifier, final String menuViewName, final String menuUrl) {
        this.menuService = menuService;
        this.pluginIdentifier = pluginIdentifier;
        this.menuName = menuName;
        this.menuCategory = menuCategory;
        this.menuViewPluginIdentifier = menuViewPluginIdentifier;
        this.menuViewName = menuViewName;
        this.menuUrl = menuUrl;
    }

    @Override
    public void multiTenantEnable() {
        if (menuUrl != null) {
            menuService.addView(pluginIdentifier, menuName, null, menuUrl);
            menuService.createItem(pluginIdentifier, menuName, menuCategory, pluginIdentifier, menuName);
        } else {
            menuService.addView(menuViewPluginIdentifier, menuViewName, menuViewName, null);
            menuService.createItem(pluginIdentifier, menuName, menuCategory, menuViewPluginIdentifier, menuViewName);
        }
    }

    @Override
    public void multiTenantDisable() {
        menuService.removeItem(pluginIdentifier, menuName);
        if (menuUrl != null) {
            menuService.removeView(pluginIdentifier, menuName);
        } else {
            menuService.removeView(menuViewPluginIdentifier, menuViewName);
        }
    }
}
