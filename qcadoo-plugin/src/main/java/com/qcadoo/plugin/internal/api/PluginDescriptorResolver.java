/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.10
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

import com.qcadoo.plugin.internal.JarEntryResource;
import java.io.File;

import com.qcadoo.plugin.internal.PluginException;
import org.springframework.core.io.Resource;

public interface PluginDescriptorResolver {
    
    /**
     * Descriptors of enabled plugins
     */
    Resource[] getDescriptors();

    /**
     * Descriptors for temporary plugins waiting for install
     */
    JarEntryResource[] getTemporaryDescriptors();

    /**
     * Extracts the plugin descriptos as a resource from a jar file
     *
     * @param file jar file with qcadoo plugin
     * @return the plugins descriptor as a resource
     * @throws PluginException
     */
    JarEntryResource getDescriptor(File file) throws PluginException;

}
