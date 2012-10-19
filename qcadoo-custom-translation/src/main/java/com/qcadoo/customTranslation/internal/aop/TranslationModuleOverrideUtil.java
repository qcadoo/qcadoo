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
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
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
                Map<String, String> translations = Maps.newHashMap();

                for (Resource resource : getPropertiesResources(basenames, locale)) {
                    translations.putAll(getTranslationsFromProperties(resource.getInputStream()));
                }

                customTranslationManagementService.addCustomTranslations(pluginIdentifier, locale, translations);
            }
        } catch (IOException e) {
            LOG.error("Cannot read properties file", e);
        }
    }

    public void removeTranslationKeysForPlugin(final String pluginIdentifier) {
        customTranslationManagementService.removeCustomTranslations(pluginIdentifier);
    }

    private Set<Resource> getPropertiesResources(final Set<String> basenames, final String locale) {
        Set<Resource> resources = new HashSet<Resource>();

        for (String basename : basenames) {
            String searchName = basename + "_" + locale + ".properties";

            resources.add(applicationContext.getResource(searchName));
        }

        return resources;
    }

    private Map<String, String> getTranslationsFromProperties(final InputStream inputStream) throws IOException {
        Map<String, String> translations = Maps.newHashMap();

        Properties properties = new Properties();
        properties.load(new InputStreamReader(inputStream, Charsets.UTF_8));

        for (Entry<Object, Object> translation : properties.entrySet()) {
            String key = (String) translation.getKey();
            String value = (String) translation.getValue();

            translations.put(key, value);
        }

        return translations;
    }

}
