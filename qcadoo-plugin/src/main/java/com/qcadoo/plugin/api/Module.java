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

package com.qcadoo.plugin.api;

import com.qcadoo.plugin.internal.api.InternalPlugin;

/**
 * Module is a part of {@link InternalPlugin} and represents some functionality, e.g. model, view, field, etc. Modules are instantiated by
 * {@link ModuleFactory}.
 * 
 * @see InternalPlugin for plugins and modules lifecycle
 */
public class Module {

    /**
     * Callback invoke once on application startup.
     */
    public void init() {
        // empty
    }

    /**
     * Callback invoke once on application startup, if plugin is or will be enabled - either {@link PluginState#ENABLED} or
     * {@link PluginState#ENABLING}.
     */
    public void enableOnStartup() {
        // empty
    }

    /**
     * Callback invoke when plugin change its state to {@link PluginState#ENABLED}.
     */
    public void enable() {
        // empty
    }

    /**
     * Callback invoke when plugin change its state from {@link PluginState#ENABLED}.
     */
    public void disable() {
        // empty
    }

    /**
     * Callback invoke once on application startup, if plugin isn't or won't be enabled - neither {@link PluginState#ENABLED} nor
     * {@link PluginState#ENABLING}.
     */
    public void disableOnStartup() {
        // empty
    }

    /**
     * Version of {@link Module#enableOnStartup()} for multi-tenant environments. Will be invoke for every tenant once. If there
     * is no tenant it won't be invoke.
     */
    public void multiTenantEnableOnStartup() {
        // empty
    }

    /**
     * Version of {@link Module#enable()} for multi-tenant environments. Will be invoke for every tenant once. If there is no
     * tenant it won't be invoke.
     */
    public void multiTenantEnable() {
        // empty
    }

    /**
     * Version of {@link Module#disable()} for multi-tenant environments. Will be invoke for every tenant once. If there is no
     * tenant it won't be invoke.
     */
    public void multiTenantDisable() {
        // empty
    }

    /**
     * Version of {@link Module#disableOnStartup()} for multi-tenant environments. Will be invoke for every tenant once. If there
     * is no tenant it won't be invoke.
     */
    public void multiTenantDisableOnStartup() {
        // empty
    }

}
