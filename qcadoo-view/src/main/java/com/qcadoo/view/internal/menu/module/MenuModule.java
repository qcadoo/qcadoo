/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.7
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.view.internal.menu.module;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.ModuleException;
import com.qcadoo.view.internal.api.InternalMenuService;

public class MenuModule extends Module {

    private final InternalMenuService menuService;

    private final String menuName;

    private final String menuCategory;

    private final String menuUrl;

    private final String pluginIdentifier;

    private final String menuViewPluginIdentifier;

    private final String menuViewName;

    private final String factoryIdentifier;

    public MenuModule(final String factoryIdentifier, final InternalMenuService menuService, final String pluginIdentifier,
            final String menuName, final String menuCategory, final String menuViewPluginIdentifier, final String menuViewName,
            final String menuUrl) {
        this.factoryIdentifier = factoryIdentifier;
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
        try {
            if (menuUrl != null) {
                menuService.addView(pluginIdentifier, menuName, null, menuUrl);
                menuService.createItem(pluginIdentifier, menuName, menuCategory, pluginIdentifier, menuName);
            } else {
                menuService.addView(menuViewPluginIdentifier, menuViewName, menuViewName, null);
                menuService.createItem(pluginIdentifier, menuName, menuCategory, menuViewPluginIdentifier, menuViewName);
            }
        } catch (Exception e) {
            throw new ModuleException(pluginIdentifier, factoryIdentifier, e);
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
