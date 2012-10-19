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
package com.qcadoo.plugins.customTranslations.internal.hooks;

import static com.qcadoo.customTranslation.constants.CustomTranslationFields.KEY;
import static com.qcadoo.customTranslation.constants.CustomTranslationFields.LOCALE;
import static com.qcadoo.customTranslation.constants.CustomTranslationFields.PLUGIN_IDENTIFIER;
import static com.qcadoo.plugins.customTranslations.constants.CustomTranslationFieldsCTM.PLUGIN_NAME;

import org.apache.commons.lang.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.plugin.api.PluginAccessor;

@Service
public class CustomTranslationModelHooksCTM {

    @Autowired
    private PluginAccessor pluginAccessor;

    @Autowired
    private TranslationService translationService;

    public void updateCustomTranslationData(final DataDefinition customTranslationDD, final Entity customTranslation) {
        String pluginIdentifier = customTranslation.getStringField(PLUGIN_IDENTIFIER);
        String key = customTranslation.getStringField(KEY);
        String locale = customTranslation.getStringField(LOCALE);

        customTranslation.setField(PLUGIN_NAME, getPluginName(pluginIdentifier));
        // customTranslation.setField(PROPERTIES_TRANSLATION, getPropertiesTranslation(key, locale));
    }

    private String getPropertiesTranslation(final String key, final String locale) {
        String translation = null;

        if ((key != null) && (locale != null)) {
            translation = translationService.translate(key, LocaleUtils.toLocale(locale));

            if (translation != null) {
                translation = translation.replace("''", "'");
            }
        }

        return translation;
    }

    private String getPluginName(final String pluginIdentifier) {
        String pluginName = null;

        if (pluginIdentifier != null) {
            pluginName = pluginAccessor.getPlugin(pluginIdentifier).getPluginInformation().getName();
        }

        return pluginName;
    }

}
