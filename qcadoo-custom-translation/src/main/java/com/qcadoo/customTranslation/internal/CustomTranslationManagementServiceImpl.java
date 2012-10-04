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
package com.qcadoo.customTranslation.internal;

import static com.qcadoo.customTranslation.constants.CustomTranslationFields.ACTIVE;
import static com.qcadoo.customTranslation.constants.CustomTranslationFields.KEY;
import static com.qcadoo.customTranslation.constants.CustomTranslationFields.LOCALE;
import static com.qcadoo.customTranslation.constants.CustomTranslationFields.PLUGIN_IDENTIFIER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.customTranslation.api.CustomTranslationManagementService;
import com.qcadoo.customTranslation.constants.CustomTranslationContants;
import com.qcadoo.customTranslation.constants.CustomTranslationFields;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchRestrictions;

@Service
public class CustomTranslationManagementServiceImpl implements CustomTranslationManagementService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Override
    public void addCustomTranslation(final String pluginIdentifier, final String key, final String locale) {
        Entity customTranslation = getCustomTranslation(pluginIdentifier, key, locale);

        if (customTranslation == null) {

            customTranslation = getCustomTranslationDD().create();

            customTranslation.setField(PLUGIN_IDENTIFIER, pluginIdentifier);
            customTranslation.setField(KEY, key);
            customTranslation.setField(LOCALE, locale);
            customTranslation.setField(ACTIVE, false);

            customTranslation.getDataDefinition().save(customTranslation);
        }
    }

    @Override
    public void removeCustomTranslation(final String pluginIdentifier, final String key, final String locale) {
        Entity customTranslation = getCustomTranslation(pluginIdentifier, key, locale);

        if (customTranslation != null) {
            customTranslation.setField(CustomTranslationFields.ACTIVE, false);

            customTranslation.getDataDefinition().save(customTranslation);
        }
    }

    @Override
    public Entity getCustomTranslation(final String pluginIdentifier, final String key, final String locale) {
        Entity customTranslation = getCustomTranslationDD().find()
                .add(SearchRestrictions.eq(PLUGIN_IDENTIFIER, pluginIdentifier)).add(SearchRestrictions.eq(KEY, key))
                .add(SearchRestrictions.eq(LOCALE, locale)).setMaxResults(1).uniqueResult();

        return customTranslation;
    }

    @Override
    public List<Entity> getCustomTranslations(final String locale) {
        List<Entity> customTranslations = getCustomTranslationDD().find().add(SearchRestrictions.eq(LOCALE, locale)).list()
                .getEntities();

        return customTranslations;
    }

    @Override
    public DataDefinition getCustomTranslationDD() {
        return dataDefinitionService.get(CustomTranslationContants.PLUGIN_IDENTIFIER,
                CustomTranslationContants.MODEL_CUSTOM_TRANSLATION);
    }

}