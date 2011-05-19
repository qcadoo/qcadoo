/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.1
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
package com.qcadoo.plugin.internal.descriptorresolver;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.qcadoo.plugin.internal.JarEntryResource;
import com.qcadoo.plugin.internal.PluginException;
import com.qcadoo.plugin.internal.api.PluginDescriptorResolver;

@Service
public class DefaultPluginDescriptorResolver implements PluginDescriptorResolver {

    private final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    @Value("#{plugin.descriptors}")
    private String descriptor;

    private final PathMatcher matcher = new AntPathMatcher();

    @Override
    public Resource[] getDescriptors() {
        try {
            return resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + descriptor);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to find classpath resources for "
                    + ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + descriptor, e);
        }
    }

    @Override
    public Resource getDescriptor(final File file) throws PluginException {
        try {

            JarFile jarFile = new JarFile(file);
            JarEntry descriptorEntry = null;

            Enumeration<JarEntry> jarEntries = jarFile.entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                if (matcher.match(descriptor, jarEntry.getName())) {
                    descriptorEntry = jarEntry;
                    break;
                }
            }

            if (descriptorEntry == null) {
                throw new PluginException("Plugin descriptor " + descriptor + " not found in " + file.getAbsolutePath());
            }

            return new JarEntryResource(file, jarFile.getInputStream(descriptorEntry));

        } catch (IOException e) {
            throw new PluginException("Plugin descriptor " + descriptor + " not found in " + file.getAbsolutePath(), e);
        }
    }

    public void setDescriptor(final String descriptor) {
        this.descriptor = descriptor;
    }

}
