/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.9
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

        List<Entity> items = dataDefinitionService.get("qcadooModel", "dictionaryItem").find()
                .createAlias("dictionary", "dictionary").add(SearchRestrictions.eq("dictionary.name", dictionary))
                .addOrder(SearchOrders.asc("name")).list().getEntities();

        List<String> keys = new ArrayList<String>();

        for (Entity item : items) {
            keys.add(item.getStringField("name"));
        }

        return keys;
    }

    @Override
    @Transactional(readOnly = true)
    @Monitorable
    public Map<String, String> getValues(final String dictionary, final Locale locale) {
        checkArgument(hasText(dictionary), "dictionary name must be given");

        List<Entity> items = dataDefinitionService.get("qcadooModel", "dictionaryItem").find()
                .createAlias("dictionary", "dictionary").add(SearchRestrictions.eq("dictionary.name", dictionary))
                .addOrder(SearchOrders.asc("name")).list().getEntities();

        Map<String, String> values = new LinkedHashMap<String, String>();

        // TODO masz translate dictionary values

        for (Entity item : items) {
            values.put(item.getStringField("name"), item.getStringField("name"));
        }

        return values;
    }

    @Override
    @Transactional(readOnly = true)
    @Monitorable
    public Set<String> getDictionaries() {
        List<Entity> dictionaries = dataDefinitionService.get("qcadooModel", "dictionary").find()
                .addOrder(SearchOrders.asc("name")).list().getEntities();

        Set<String> names = new HashSet<String>();

        for (Entity dictionary : dictionaries) {
            if ((Boolean) dictionary.getField("active")) {
                names.add(dictionary.getStringField("name"));
            }
        }

        return names;
    }

    @Override
    @Transactional
    @Monitorable
    public void createIfNotExists(final String pluginIdentifier, final String name, final String... values) {
        SearchResult serachResult = dataDefinitionService.get("qcadooModel", "dictionary").find()
                .add(SearchRestrictions.eq("name", name)).list();
        if (serachResult.getTotalNumberOfEntities() > 0) {
            Entity dictionaryEntity = serachResult.getEntities().get(0);
            dictionaryEntity.setField("active", true);
            dataDefinitionService.get("qcadooModel", "dictionary").save(dictionaryEntity);
            return;
        }

        Entity dictionary = dataDefinitionService.get("qcadooModel", "dictionary").create();
        dictionary.setField("pluginIdentifier", pluginIdentifier);
        dictionary.setField("name", name);
        dictionary.setField("active", true);
        dictionary = dataDefinitionService.get("qcadooModel", "dictionary").save(dictionary);

        for (String value : values) {
            Entity item = dataDefinitionService.get("qcadooModel", "dictionaryItem").create();
            item.setField("dictionary", dictionary);
            item.setField("description", "");
            item.setField("name", value);
            dataDefinitionService.get("qcadooModel", "dictionaryItem").save(item);
        }
    }

    @Override
    public String getName(final String dictionaryName, final Locale locale) {
        Entity dictionary = dataDefinitionService.get("qcadooModel", "dictionary").find()
                .add(SearchRestrictions.eq("name", dictionaryName)).setMaxResults(1).uniqueResult();
        return translationService.translate(dictionary.getStringField("pluginIdentifier") + "." + dictionaryName + ".dictionary",
                locale);
    }

    @Override
    @Transactional
    @Monitorable
    public void disable(final String pluginIdentifier, final String name) {
        SearchResult serachResult = dataDefinitionService.get("qcadooModel", "dictionary").find()
                .add(SearchRestrictions.eq("name", name)).list();
        if (serachResult.getTotalNumberOfEntities() > 0) {
            Entity dictionaryEntity = serachResult.getEntities().get(0);
            dictionaryEntity.setField("active", false);
            dataDefinitionService.get("qcadooModel", "dictionary").save(dictionaryEntity);
        }
    }

}
