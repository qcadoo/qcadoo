/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.6
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
package com.qcadoo.view.internal.menu;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.aop.Monitorable;
import com.qcadoo.model.api.search.SearchOrders;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.plugin.api.PluginUtils;
import com.qcadoo.security.api.SecurityRole;
import com.qcadoo.security.api.SecurityRolesService;
import com.qcadoo.view.api.utils.TranslationUtilsService;
import com.qcadoo.view.internal.api.InternalMenuService;
import com.qcadoo.view.internal.api.ViewDefinitionService;
import com.qcadoo.view.internal.menu.items.UrlMenuItem;
import com.qcadoo.view.internal.menu.items.ViewDefinitionMenuItemItem;
import com.qcadoo.view.internal.security.SecurityViewDefinitionRoleResolver;

@Service
public final class MenuServiceImpl implements InternalMenuService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Autowired
    private SecurityRolesService securityRolesService;

    @Autowired
    private SecurityViewDefinitionRoleResolver viewDefinitionRoleResolver;

    @Autowired
    private TranslationUtilsService translationUtilsService;

    @Autowired
    private ViewDefinitionService viewDefinitionService;

    private static final String VIEW = "view";

    private static final String ITEM = "item";

    private static final String PLUGIN_IDENTIFIER = "pluginIdentifier";

    private static final String NAME = "name";

    private static final String CATEGORY = "category";

    private static final String SUCCESSION = "succession";

    private static final String URL = "url";

    @Override
    @Transactional(readOnly = true)
    @Monitorable
    public MenuDefinition getMenu(final Locale locale) {

        MenuDefinition menuDefinition = new MenuDefinition();

        List<Entity> menuCategories = getDataDefinition(CATEGORY).find().addOrder(SearchOrders.asc(SUCCESSION)).list()
                .getEntities();

        for (Entity menuCategory : menuCategories) {
            String label = menuCategory.getStringField(NAME);
            String categoryDescription = translationUtilsService.getCategoryDescriptionTranslation(menuCategory, locale);

            if (menuCategory.getStringField(PLUGIN_IDENTIFIER) != null) {
                label = translationUtilsService.getCategoryTranslation(menuCategory, locale);
            }

            MenuItemsGroup category = new MenuItemsGroup(menuCategory.getStringField(NAME), label, categoryDescription);

            List<Entity> menuItems = getDataDefinition(ITEM).find().add(SearchRestrictions.belongsTo(CATEGORY, menuCategory))
                    .addOrder(SearchOrders.asc(SUCCESSION)).list().getEntities();

            for (Entity menuItem : menuItems) {
                if (!(Boolean) menuItem.getField("active")) {
                    continue;
                }

                Entity menuView = menuItem.getBelongsToField(VIEW);

                String itemLabel = menuItem.getStringField(NAME);
                String itemDescription = translationUtilsService.getItemDescriptionTranslation(menuItem, locale);

                if (menuItem.getStringField(PLUGIN_IDENTIFIER) != null) {
                    itemLabel = translationUtilsService.getItemTranslation(menuItem, locale);
                }

                if (canAccess(menuView.getStringField(PLUGIN_IDENTIFIER), menuView.getStringField(NAME))) {
                    MenuItem newMenuItem = null;
                    if (menuView.getStringField(URL) == null) {
                        newMenuItem = new ViewDefinitionMenuItemItem(menuItem.getStringField(NAME), itemLabel, itemDescription,
                                menuView.getStringField(PLUGIN_IDENTIFIER), menuView.getStringField(NAME));
                    } else {
                        newMenuItem = new UrlMenuItem(menuItem.getStringField(NAME), itemLabel, itemDescription, null,
                                menuView.getStringField(URL));
                    }
                    category.addItem(newMenuItem);
                }
            }

            if ("administration".equals(category.getName())) {
                menuDefinition.setAdministrationCategory(category);
            } else if ("home".equals(category.getName())) {
                menuDefinition.setHomeCategory(category);
            } else if (!category.getItems().isEmpty()) {
                menuDefinition.addItem(category);
            }
        }

        return menuDefinition;
    }

    private boolean canAccess(final String pluginIdentifier, final String viewName) {
        if (!PluginUtils.isEnabled(pluginIdentifier)) {
            return false;
        }

        SecurityRole viewRole = viewDefinitionRoleResolver.getRoleForView(pluginIdentifier, viewName);
        if (!securityRolesService.canAccess(viewRole)) {
            return false;
        }

        return true;
    }

    @Override
    @Transactional
    public void addView(final String pluginIdentifier, final String viewName, final String view, final String url) {
        Entity menuView = getView(pluginIdentifier, viewName);

        if (menuView != null) {
            return;
        }

        if (url == null) {
            if (!viewDefinitionService.viewExists(pluginIdentifier, viewName)) {
                throw new IllegalStateException("View " + pluginIdentifier + "/" + view + " does not exist.");
            }
        }

        menuView = getDataDefinition(VIEW).create();
        menuView.setField(PLUGIN_IDENTIFIER, pluginIdentifier);
        menuView.setField(NAME, viewName);
        menuView.setField(URL, url);
        menuView.setField(VIEW, view);
        getDataDefinition(VIEW).save(menuView);
    }

    @Override
    @Transactional
    public void removeView(final String pluginIdentifier, final String viewName) {
        Entity menuView = getView(pluginIdentifier, viewName);
        if (menuView == null) {
            return;
        }

        Collection<Entity> itemsToThisView = getItemsToView(menuView);
        for (Entity itemToThisView : itemsToThisView) {
            getDataDefinition(ITEM).delete(itemToThisView.getId());
        }

        getDataDefinition(VIEW).delete(menuView.getId());
    }

    @Override
    @Transactional
    public void createCategory(final String pluginIdentifier, final String categoryName) {
        Entity menuCategory = getCategory(pluginIdentifier, categoryName);

        if (menuCategory != null) {
            return;
        }

        menuCategory = getDataDefinition(CATEGORY).create();
        menuCategory.setField(PLUGIN_IDENTIFIER, pluginIdentifier);
        menuCategory.setField(NAME, categoryName);
        menuCategory.setField("accessible", true);
        menuCategory.setField(SUCCESSION, getTotalNumberOfCategories());
        getDataDefinition(CATEGORY).save(menuCategory);
    }

    @Override
    @Transactional
    public void removeCategory(final String pluginIdentifier, final String categoryName) {
        Entity menuCategory = getCategory(pluginIdentifier, categoryName);

        if (menuCategory == null) {
            return;
        }

        if (menuCategory.getHasManyField("items").size() == 0) {
            getDataDefinition(CATEGORY).delete(menuCategory.getId());
        }
    }

    @Override
    @Transactional
    public void createItem(final String pluginIdentifier, final String name, final String category,
            final String viewPluginIdentifier, final String viewName) {
        Entity menuItem = getItem(pluginIdentifier, name);

        Entity menuCategory = getCategory(category);
        Entity menuView = getView(viewPluginIdentifier, viewName);

        if (menuCategory == null) {
            throw new IllegalStateException("Cannot find menu category " + category + " for item " + pluginIdentifier + "."
                    + name);
        }

        if (menuView == null) {
            throw new IllegalStateException("Cannot find menu view " + viewPluginIdentifier + "." + viewName + " for item "
                    + pluginIdentifier + "." + name);
        }

        if (menuItem == null) {
            menuItem = getDataDefinition(ITEM).create();
            menuItem.setField(PLUGIN_IDENTIFIER, pluginIdentifier);
            menuItem.setField(NAME, name);
            menuItem.setField("active", true);
        }

        if (menuItem == null || !menuView.equals(menuItem.getField(VIEW))) {
            menuItem.setField(VIEW, menuView);
            menuItem.setField(CATEGORY, menuCategory);
            menuItem.setField(SUCCESSION, getTotalNumberOfItems(menuCategory));
            getDataDefinition(ITEM).save(menuItem);
        }
    }

    @Override
    @Transactional
    public void removeItem(final String pluginIdentifier, final String name) {
        Entity menuItem = getItem(pluginIdentifier, name);
        if (menuItem == null) {
            return;
        }
        getDataDefinition(ITEM).delete(menuItem.getId());
    }

    private DataDefinition getDataDefinition(final String entityName) {
        return dataDefinitionService.get("qcadooView", entityName);
    }

    private int getTotalNumberOfItems(final Entity category) {
        return getDataDefinition(ITEM).find().add(SearchRestrictions.belongsTo(CATEGORY, category)).list()
                .getTotalNumberOfEntities() + 1;
    }

    private int getTotalNumberOfCategories() {
        return getDataDefinition(CATEGORY).find().list().getTotalNumberOfEntities() + 1;
    }

    private Entity getCategory(final String pluginIdentifier, final String categoryName) {
        return getDataDefinition(CATEGORY).find().add(SearchRestrictions.eq(NAME, categoryName))
                .add(SearchRestrictions.eq(PLUGIN_IDENTIFIER, pluginIdentifier)).setMaxResults(1).uniqueResult();
    }

    private Entity getCategory(final String categoryName) {
        return getDataDefinition(CATEGORY).find().add(SearchRestrictions.eq(NAME, categoryName)).setMaxResults(1).uniqueResult();
    }

    private Entity getItem(final String pluginIdentifier, final String itemName) {
        return getDataDefinition(ITEM).find().add(SearchRestrictions.eq(NAME, itemName))
                .add(SearchRestrictions.eq(PLUGIN_IDENTIFIER, pluginIdentifier)).setMaxResults(1).uniqueResult();
    }

    private Collection<Entity> getItemsToView(final Entity view) {
        return getDataDefinition(ITEM).find().add(SearchRestrictions.belongsTo(VIEW, view)).list().getEntities();
    }

    private Entity getView(final String pluginIdentifier, final String viewName) {
        return getDataDefinition(VIEW).find().add(SearchRestrictions.eq(NAME, viewName))
                .add(SearchRestrictions.eq(PLUGIN_IDENTIFIER, pluginIdentifier)).setMaxResults(1).uniqueResult();
    }

}
