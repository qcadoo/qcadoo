package com.qcadoo.view.internal.menu.module;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.PluginState;
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
    public void init(final PluginState state) {
        if (menuUrl != null) {
            menuService.createViewIfNotExists(pluginIdentifier, menuName, null, menuUrl);
            menuService.createItemIfNotExists(pluginIdentifier, menuName, menuCategory, pluginIdentifier, menuName);
        } else {
            menuService.createViewIfNotExists(menuViewPluginIdentifier, menuViewName, menuViewName, null);
            menuService.createItemIfNotExists(pluginIdentifier, menuName, menuCategory, menuViewPluginIdentifier, menuViewName);
        }

    }

    @Override
    public void enable() {
        if (menuUrl != null) {
            menuService.enableView(pluginIdentifier, menuName);
        }
        menuService.enableItem(pluginIdentifier, menuName);
    }

    @Override
    public void disable() {
        if (menuUrl != null) {
            menuService.disableView(pluginIdentifier, menuName);
        }
        menuService.disableItem(pluginIdentifier, menuName);
    }
}
