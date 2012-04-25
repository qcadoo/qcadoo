/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.5
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
package com.qcadoo.model.internal.dictionaries;

import static com.google.common.base.Preconditions.checkArgument;
import static com.qcadoo.model.constants.DictionaryFields.ACTIVE;
import static com.qcadoo.model.constants.DictionaryFields.NAME;
import static com.qcadoo.model.constants.QcadooModelConstants.MODEL_DICTIONARY;
import static com.qcadoo.model.constants.QcadooModelConstants.MODEL_DICTIONARY_ITEM;
import static com.qcadoo.model.constants.QcadooModelConstants.PLUGIN_IDENTIFIER;
import static org.springframework.util.StringUtils.hasText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.aop.Monitorable;
import com.qcadoo.model.api.search.SearchOrders;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.model.api.search.SearchResult;
import com.qcadoo.model.internal.api.InternalDictionaryService;

@Service
public final class DictionaryServiceImpl implements InternalDictionaryService {

    @Autowired
    private TranslationService translationService;

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Override
    @Transactional(readOnly = true)
    @Monitorable
    public List<String> getKeys(final String dictionary) {
        checkArgument(hasText(dictionary), "dictionary name must be given");

        List<Entity> items = dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_DICTIONARY_ITEM).find()
                .createAlias(MODEL_DICTIONARY, MODEL_DICTIONARY).add(SearchRestrictions.eq("dictionary.name", dictionary))
                .addOrder(SearchOrders.asc(NAME)).list().getEntities();

        List<String> keys = new ArrayList<String>();

        for (Entity item : items) {
            keys.add(item.getStringField(NAME));
        }

        return keys;
    }

    @Override
    @Transactional(readOnly = true)
    @Monitorable
    public Map<String, String> getValues(final String dictionary, final Locale locale) {
        checkArgument(hasText(dictionary), "dictionary name must be given");

        List<Entity> items = dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_DICTIONARY_ITEM).find()
                .createAlias(MODEL_DICTIONARY, MODEL_DICTIONARY).add(SearchRestrictions.eq("dictionary.name", dictionary))
                .addOrder(SearchOrders.asc(NAME)).list().getEntities();

        Map<String, String> values = new LinkedHashMap<String, String>();

        // TODO MAKU translate dictionary values

        for (Entity item : items) {
            values.put(item.getStringField(NAME), item.getStringField(NAME));
        }

        return values;
    }

    @Override
    @Transactional(readOnly = true)
    @Monitorable
    public Set<String> getDictionaries() {
        List<Entity> dictionaries = dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_DICTIONARY).find()
                .addOrder(SearchOrders.asc(NAME)).list().getEntities();

        Set<String> names = new HashSet<String>();

        for (Entity dictionary : dictionaries) {
            if ((Boolean) dictionary.getField(ACTIVE)) {
                names.add(dictionary.getStringField(NAME));
            }
        }

        return names;
    }

    @Override
    @Transactional
    @Monitorable
    public void createIfNotExists(final String pluginIdentifier, final String name, final String... values) {
        SearchResult serachResult = dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_DICTIONARY).find()
                .add(SearchRestrictions.eq(NAME, name)).list();
        if (serachResult.getTotalNumberOfEntities() > 0) {
            Entity dictionaryEntity = serachResult.getEntities().get(0);
            dictionaryEntity.setField(ACTIVE, true);
            dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_DICTIONARY).save(dictionaryEntity);
            return;
        }

        Entity dictionary = dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_DICTIONARY).create();
        dictionary.setField("pluginIdentifier", pluginIdentifier);
        dictionary.setField(NAME, name);
        dictionary.setField(ACTIVE, true);
        dictionary = dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_DICTIONARY).save(dictionary);

        for (String value : values) {
            Entity item = dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_DICTIONARY_ITEM).create();
            item.setField(MODEL_DICTIONARY, dictionary);
            item.setField("description", "");
            item.setField(NAME, value);
            dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_DICTIONARY_ITEM).save(item);
        }
    }

    @Override
    public String getName(final String dictionaryName, final Locale locale) {
        Entity dictionary = dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_DICTIONARY).find()
                .add(SearchRestrictions.eq(NAME, dictionaryName)).setMaxResults(1).uniqueResult();
        return translationService.translate(dictionary.getStringField("pluginIdentifier") + "." + dictionaryName + ".dictionary",
                locale);
    }

    @Override
    @Transactional
    @Monitorable
    public void disable(final String pluginIdentifier, final String name) {
        SearchResult serachResult = dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_DICTIONARY).find()
                .add(SearchRestrictions.eq(NAME, name)).list();
        if (serachResult.getTotalNumberOfEntities() > 0) {
            Entity dictionaryEntity = serachResult.getEntities().get(0);
            dictionaryEntity.setField(ACTIVE, false);
            dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_DICTIONARY).save(dictionaryEntity);
        }
    }

}
