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

    @Autowired
    private TranslationService translationService;

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
                category.getStringField("pluginIdentifier") + ".menu." + category.getStringField("name"),
                "qcadooView.menu." + category.getStringField("name"), locale);
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
        Entity categoryEntity = item.getBelongsToField("category");
        return translationService.translate(
                item.getStringField("pluginIdentifier") + ".menu." + categoryEntity.getStringField("name") + "."
                        + item.getStringField("name"),
                "qcadooView.menu." + categoryEntity.getStringField("name") + "." + item.getStringField("name"), locale);
    }

}
