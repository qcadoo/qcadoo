/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.7
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
package com.qcadoo.customTranslation.internal.aop;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.qcadoo.customTranslation.api.CustomTranslationManagementService;
import com.qcadoo.customTranslation.constants.CustomTranslationContants;
import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.plugin.api.PluginStateResolver;

@Service
public class TranslationModuleOverrideUtil {

    private static final Logger LOG = LoggerFactory.getLogger(TranslationModuleOverrideUtil.class);

    @Value("${useCustomTranslations}")
    private boolean useCustomTranslations;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private PluginStateResolver pluginStateResolver;

    @Autowired
    private CustomTranslationManagementService customTranslationManagementService;

    public boolean shouldOverride() {
        return pluginStateResolver.isEnabled(CustomTranslationContants.PLUGIN_IDENTIFIER) && useCustomTranslations;
    }

    public void addTranslationKeysForPlugin(final String pluginIdentifier, final Set<String> basenames) {
        try {
            for (String locale : translationService.getLocales().keySet()) {
                for (Resource resource : getPropertiesResources(basenames, locale)) {
                    for (Object key : getTranslationKeysFromProperties(resource.getInputStream())) {
                        customTranslationManagementService.addCustomTranslation(pluginIdentifier, (String) key, locale);
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("Cannot read messages file", e);
        }
    }

    public void removeTranslationKeysForPlugin(final String pluginIdentifier, final Set<String> basenames) {
        try {
            for (String locale : translationService.getLocales().keySet()) {
                for (Resource resource : getPropertiesResources(basenames, locale)) {
                    for (Object key : getTranslationKeysFromProperties(resource.getInputStream())) {
                        customTranslationManagementService.removeCustomTranslation(pluginIdentifier, (String) key, locale);
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("Cannot read messages file", e);
        }
    }

    private List<Resource> getPropertiesResources(final Set<String> basenames, final String locale) {
        List<Resource> resources = new ArrayList<Resource>();
        for (String basename : basenames) {
            String searchName = basename + "_" + locale + ".properties";

            resources.add(applicationContext.getResource(searchName));
        }

        return resources;
    }

    private Set<Object> getTranslationKeysFromProperties(final InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);

        return properties.keySet();
    }

}
