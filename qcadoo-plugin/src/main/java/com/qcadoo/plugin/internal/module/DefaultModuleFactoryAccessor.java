/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.0
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

package com.qcadoo.plugin.internal.module;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.plugin.api.Plugin;
import com.qcadoo.plugin.api.PluginState;
import com.qcadoo.plugin.api.PluginUtil;
import com.qcadoo.plugin.internal.api.InternalPlugin;
import com.qcadoo.plugin.internal.api.ModuleFactoryAccessor;
import com.qcadoo.tenant.api.MultiTenantCallback;
import com.qcadoo.tenant.api.MultiTenantUtil;

public final class DefaultModuleFactoryAccessor implements ModuleFactoryAccessor {

    private final Map<String, ModuleFactory<?>> moduleFactoryRegistry = new LinkedHashMap<String, ModuleFactory<?>>();

    @Override
    public void init(final List<Plugin> plugins) {
        for (ModuleFactory<?> moduleFactory : moduleFactoryRegistry.values()) {
            moduleFactory.preInit();

            for (Plugin plugin : plugins) {
                for (Module module : ((InternalPlugin) plugin).getModules(moduleFactory)) {
                    module.init();
                }
            }

            moduleFactory.postInit();
        }

        for (ModuleFactory<?> moduleFactory : moduleFactoryRegistry.values()) {
            for (final Plugin plugin : plugins) {
                for (final Module module : ((InternalPlugin) plugin).getModules(moduleFactory)) {
                    if (plugin.hasState(PluginState.ENABLED) || plugin.hasState(PluginState.ENABLING)) {
                        module.enableOnStartup();

                        MultiTenantUtil.doInMultiTenantContext(new MultiTenantCallback() {

                            @Override
                            public void invoke() {
                                if (PluginUtil.isPluginEnabled(plugin)) {
                                    module.multiTenantEnableOnStartup();
                                }
                            }

                        });
                    } else {
                        module.disableOnStartup();

                        MultiTenantUtil.doInMultiTenantContext(new MultiTenantCallback() {

                            @Override
                            public void invoke() {
                                if (!PluginUtil.isPluginEnabled(plugin)) {
                                    module.multiTenantDisableOnStartup();
                                }
                            }

                        });
                    }
                }
            }
        }
    }

    @Override
    public ModuleFactory<?> getModuleFactory(final String identifier) {
        if (!moduleFactoryRegistry.containsKey(identifier)) {
            throw new IllegalStateException("ModuleFactory " + identifier + " is not defined");
        }
        return moduleFactoryRegistry.get(identifier);
    }

    public void setModuleFactories(final List<ModuleFactory<?>> moduleFactories) {
        for (ModuleFactory<?> moduleFactory : moduleFactories) {
            if (moduleFactoryRegistry.containsKey(moduleFactory.getIdentifier())) {
                throw new IllegalStateException("ModuleFactory " + moduleFactory.getClass().getCanonicalName()
                        + " try to overwrite existing module with identifier " + moduleFactory.getIdentifier());
            }
            moduleFactoryRegistry.put(moduleFactory.getIdentifier(), moduleFactory);
        }
    }

}
