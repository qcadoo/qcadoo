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

package com.qcadoo.plugin.internal.api;

import java.util.Collection;
import java.util.List;

import com.qcadoo.plugin.api.Plugin;
import com.qcadoo.plugin.internal.dependencymanager.PluginDependencyResult;

public interface PluginDependencyManager {

    PluginDependencyResult getDependenciesToEnable(List<Plugin> plugins);

    PluginDependencyResult getDependenciesToDisable(List<Plugin> plugins);

    List<Plugin> sortPluginsInDependencyOrder(Collection<Plugin> plugins);

    PluginDependencyResult getDependenciesToUninstall(List<Plugin> plugins);

    PluginDependencyResult getDependenciesToUpdate(Plugin existingPlugin, Plugin newPlugin);

}
