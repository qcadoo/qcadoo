/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.1
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

import java.util.Set;

import org.springframework.core.io.Resource;

import com.qcadoo.plugin.internal.JarEntryResource;

public interface PluginDescriptorParser {

    /**
     * Parse a plugin from the classpath
     * 
     * @param resource
     *            plugins descriptor in the classpath
     */
    InternalPlugin parse(final Resource resource, final boolean ignoreModules);

    /**
     * Parse a plugin from outside the classpath
     * 
     * @param resource
     *            jar entry with the plugin descriptor
     */
    InternalPlugin parse(JarEntryResource resource, final boolean ignoreModules);

    /**
     * Load enabled plugins
     */
    Set<InternalPlugin> loadPlugins();

    /**
     * Temporary plugins waiting for install
     */
    Set<InternalPlugin> getTemporaryPlugins();
}
