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

import org.jdom.Element;

/**
 * Factory is responsible for parsing descriptors and creating instances of {@link Module}.
 * 
 * @see Plugin for plugin lifecycle
 */
public abstract class ModuleFactory<T extends Module> {

    /**
     * Callback is invoke once on application startup, before the {@link Module#init()}.
     */
    public void preInit() {
        // empty
    }

    /**
     * Callback is invoke once on application startup, after the {@link Module#init()}.
     */
    public void postInit() {
        // empty
    }

    /**
     * Parses descriptor and creates instance of {@link Module}.
     * 
     * @param pluginIdentifier
     *            plugin identifier
     * @param element
     *            xml element describing module
     * @return module instance
     */
    public abstract T parse(String pluginIdentifier, Element element);

    /**
     * Identifier is used to distinguish the type of the module. It is equal to the name of the tag in section "modules" in plugin
     * descriptor.
     * 
     * @return module identifier
     */
    public abstract String getIdentifier();

}