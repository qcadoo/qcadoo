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
package com.qcadoo.plugin.internal.descriptorresolver;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(DefaultPluginDescriptorResolver.class);

    private final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    @Value("#{plugin.descriptors}")
    private String descriptor;

    @Value("#{plugin.pluginsTmpPath}")
    private String pluginsTmpPath;

    private final PathMatcher matcher = new AntPathMatcher();

    @Override
    public Resource[] getDescriptors() {
        try {
            Resource[] descriptors = resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + descriptor);
            HashSet<Resource> uniqueDescriptors = new HashSet<Resource>(Arrays.asList(descriptors));
            return uniqueDescriptors.toArray(new Resource[uniqueDescriptors.size()]);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to find classpath resources for "
                    + ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + descriptor, e);
        }
    }

    @Override
    public JarEntryResource[] getTemporaryDescriptors() {
        if (pluginsTmpPath == null || pluginsTmpPath.trim().isEmpty()) {
            return new JarEntryResource[0];
        }
        File pluginsTmpFile = new File(pluginsTmpPath);
        if (!pluginsTmpFile.exists()) {
            LOG.warn("Plugins temporary directory does not exist: " + pluginsTmpPath);
            return new JarEntryResource[0];
        }
        try {
            FilenameFilter jarsFilter = new WildcardFileFilter("*.jar");
            if (!pluginsTmpFile.exists()) {
                throw new IOException();
            }
            File[] pluginJars = pluginsTmpFile.listFiles(jarsFilter);
            JarEntryResource[] pluginDescriptors = new JarEntryResource[pluginJars.length];
            for (int i = 0; i < pluginJars.length; ++i) {
                File jarRes = pluginJars[i];
                JarEntryResource descriptorResource = getDescriptor(jarRes);
                pluginDescriptors[i] = descriptorResource;
            }
            return pluginDescriptors;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to reading plugins from " + pluginsTmpPath, e);
        }
    }

    @Override
    public JarEntryResource getDescriptor(final File file) throws PluginException {
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
