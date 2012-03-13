/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.3
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
package com.qcadoo.view.api.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.model.api.Entity;

/**
 * Helper service for translations
 * 
 * @since 0.4.0
 */
@Service
public class TranslationUtilsService {

    private static final String MENU = ".menu.";

    @Autowired
    private TranslationService translationService;

    private static final String CATEGORY = "category";

    private static final String NAME = "name";

    private static final String PLUGIN_IDENTIFIER = "pluginIdentifier";

    /**
     * Returns menu category translation
     * 
     * @param category
     *            category entity
     * @param locale
     *            localization
     * @return category translation
     */
    public String getCategoryTranslation(final Entity category, final Locale locale) {
        return translationService.translate(
                category.getStringField(PLUGIN_IDENTIFIER) + MENU + category.getStringField(NAME), "qcadooView.menu."
                        + category.getStringField(NAME), locale);
    }

    /**
     * Returns menu category description translation
     * 
     * @param category
     *            category entity
     * @param locale
     *            localization
     * @return category description translation or empty String if translation does not exists
     * 
     * @since 1.1.3
     */
    public String getCategoryDescriptionTranslation(final Entity category, final Locale locale) {
        String translationKey = category.getStringField(PLUGIN_IDENTIFIER) + MENU + category.getStringField(NAME)
                + ".description";
        return translateAndIgnoreMissingMessages(translationKey, locale);
    }

    /**
     * Returns menu item translation
     * 
     * @param item
     *            item entity
     * @param locale
     *            localization
     * @return item translation
     */
    public String getItemTranslation(final Entity item, final Locale locale) {
        Entity categoryEntity = item.getBelongsToField(CATEGORY);
        return translationService.translate(
                item.getStringField(PLUGIN_IDENTIFIER) + MENU + categoryEntity.getStringField(NAME) + '.'
                        + item.getStringField(NAME),
                "qcadooView.menu." + categoryEntity.getStringField(NAME) + '.' + item.getStringField(NAME), locale);
    }

    /**
     * Returns menu item description translation
     * 
     * @param item
     *            category entity
     * @param locale
     *            localization
     * @return item description translation or empty String if translation does not exists
     * 
     * @since 1.1.3
     */
    public String getItemDescriptionTranslation(final Entity item, final Locale locale) {
        Entity category = item.getBelongsToField(CATEGORY);
        String translationKey = item.getStringField(PLUGIN_IDENTIFIER) + MENU + category.getStringField(NAME) + '.'
                + item.getStringField(NAME) + ".description";
        return translateAndIgnoreMissingMessages(translationKey, locale);
    }

    /**
     * Returns translation for given key or empty string if translation has been not found.
     * 
     * @param translationKey
     *            translation key
     * @param locale
     *            localization
     * @return translated message or empty String if translation does not exists
     */
    private String translateAndIgnoreMissingMessages(final String translationKey, final Locale locale) {
        String translation = translationService.translate(translationKey, locale);
        if (translation == null || translation.equals(TranslationService.DEFAULT_MISSING_MESSAGE)
                || translationKey.equals(translation)) {
            return "";
        }
        return translation;
    }
}
