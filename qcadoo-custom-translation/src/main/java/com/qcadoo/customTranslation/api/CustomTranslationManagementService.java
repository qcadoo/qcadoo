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

import java.util.List;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;

/**
 * Service for managing custom translations.
 * 
 * @since 1.8.0
 */
public interface CustomTranslationManagementService {

    /**
     * Add custom translation with given plugin identifier, key and locale
     * 
     * @param pluginIdentifier
     *            plugin identifier
     * @param key
     *            translation key
     * @param locale
     *            locale
     */
    void addCustomTranslation(final String pluginIdentifier, final String key, final String locale);

    /**
     * Remove custom translation with given plugin identifier, key and locale
     * 
     * @param pluginIdentifier
     *            plugin identifier
     * @param key
     *            translation key
     * @param locale
     *            locale
     */
    void removeCustomTranslation(final String pluginIdentifier, final String key, final String locale);

    /**
     * Gets custom translation with given plugin identifier, key and locale
     * 
     * @param pluginIdentifier
     *            plugin identifier
     * @param key
     *            translation key
     * @param locale
     *            locale
     * 
     * @return the custom translation
     */
    Entity getCustomTranslation(final String pluginIdentifier, final String key, final String locale);

    /**
     * Gets list of custom translations with given locale
     * 
     * @param locale
     *            locale
     * 
     * @return the list of custom translations
     */
    List<Entity> getCustomTranslations(final String locale);

    /**
     * Gets DataDefinition for model custom translation
     * 
     * @return the custom translation model data definition
     */
    DataDefinition getCustomTranslationDD();

}