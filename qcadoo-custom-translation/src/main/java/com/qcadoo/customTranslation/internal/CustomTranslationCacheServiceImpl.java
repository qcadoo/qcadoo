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

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.qcadoo.customTranslation.api.CustomTranslationCacheService;

@Service
public class CustomTranslationCacheServiceImpl implements CustomTranslationCacheService {

    private final Map<String, Map<String, String>> customTranslations;

    public CustomTranslationCacheServiceImpl() {
        this.customTranslations = Maps.newHashMap();
    }

    @Override
    public void addCustomTranslation(final String key, final String locale, final String customTranslation) {
        Map<String, String> localeAndCustomTranslation = Maps.newHashMap();
        localeAndCustomTranslation.put(locale, customTranslation);

        customTranslations.put(key, localeAndCustomTranslation);
    }

    @Override
    public void updateCustomTranslation(final String key, final String locale, final String customTranslation) {
        if (isCustomTranslationAdded(key)) {
            customTranslations.get(key).put(locale, customTranslation);
        }
    }

    @Override
    public String getCustomTranslation(final String key, final String locale) {
        if (customTranslations.containsKey(key)) {
            if (customTranslations.get(key).containsKey(locale)) {
                return customTranslations.get(key).get(locale);
            }
        }
        return null;
    }

    @Override
    public Map<String, Map<String, String>> getCustomTranslations() {
        return customTranslations;
    }

    @Override
    public boolean isCustomTranslationAdded(final String key) {
        return customTranslations.containsKey(key);
    }

    @Override
    public boolean isCustomTranslationActive(final String key, final String locale) {
        if (customTranslations.containsKey(key)) {
            if (customTranslations.get(key).containsKey(locale)) {
                return customTranslations.get(key).get(locale) != null;
            }
        }

        return false;
    }

}