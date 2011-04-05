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

import java.util.List;
import java.util.Set;

public interface Plugin {

    String getIdentifier();

    Version getVersion();

    PluginState getState();

    PluginInformation getPluginInformation();

    Set<PluginDependencyInformation> getRequiredPlugins();

    boolean isSystemPlugin();

    void changeStateTo(PluginState state);

    String getFilename();

    int compareVersion(Version version);

    boolean hasState(PluginState expectedState);

    List<Module> getModules(ModuleFactory<?> moduleFactory);

}
