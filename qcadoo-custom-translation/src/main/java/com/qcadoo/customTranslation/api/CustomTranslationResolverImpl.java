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
package com.qcadoo.customTranslation.api;

import static com.qcadoo.customTranslation.constants.CustomTranslationFields.ACTIVE;
import static com.qcadoo.customTranslation.constants.CustomTranslationFields.CUSTOM_TRANSLATION;
import static com.qcadoo.customTranslation.constants.CustomTranslationFields.KEY;
import static com.qcadoo.customTranslation.constants.CustomTranslationFields.LOCALE;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.qcadoo.customTranslation.constants.CustomTranslationContants;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.model.api.search.SearchResult;

@Service
public class CustomTranslationResolverImpl implements CustomTranslationResolver {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Autowired
    private CustomTranslationManagementService customTranslationManagementService;

    @Override
    public boolean isCustomTranslationActive(final String key, final Locale locale) {
        SearchResult searchResult = dataDefinitionService
                .get(CustomTranslationContants.PLUGIN_IDENTIFIER, CustomTranslationContants.MODEL_CUSTOM_TRANSLATION).find()
                .add(SearchRestrictions.eq(KEY, key)).add(SearchRestrictions.eq(LOCALE, locale.getLanguage()))
                .add(SearchRestrictions.eq(ACTIVE, true)).list();

        return !searchResult.getEntities().isEmpty();
    }

    @Override
    public String getCustomTranslation(final String key, final Locale locale, final String[] args) {
        Entity customTranslation = dataDefinitionService
                .get(CustomTranslationContants.PLUGIN_IDENTIFIER, CustomTranslationContants.MODEL_CUSTOM_TRANSLATION).find()
                .add(SearchRestrictions.eq(KEY, key)).add(SearchRestrictions.eq(LOCALE, locale.getLanguage()))
                .add(SearchRestrictions.eq(ACTIVE, true)).setMaxResults(1).uniqueResult();

        if (customTranslation == null) {
            return null;
        } else {
            String translation = customTranslation.getStringField(CUSTOM_TRANSLATION);

            translation = translation.replace("'", "''");

            Object[] argsToUse = null;

            if (ObjectUtils.isEmpty(args)) {
                argsToUse = new Object[0];
            } else {
                argsToUse = args;
            }

            MessageFormat messageFormat = new MessageFormat(translation);

            synchronized (messageFormat) {
                return messageFormat.format(argsToUse);
            }
        }
    }

}