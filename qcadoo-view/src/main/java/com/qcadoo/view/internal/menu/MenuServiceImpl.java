/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.1
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

    @Override
    @Transactional(readOnly = true)
    @Monitorable
    public MenuDefinition getMenu(final Locale locale) {

        MenuDefinition menuDefinition = new MenuDefinition();

        List<Entity> menuCategories = getDataDefinition("category").find().addOrder(SearchOrders.asc("succession")).list()
                .getEntities();

        for (Entity menuCategory : menuCategories) {
            String label = menuCategory.getStringField("name");

            if (menuCategory.getStringField("pluginIdentifier") != null) {
                label = translationUtilsService.getCategoryTranslation(menuCategory, locale);
            }

            MenuItemsGroup category = new MenuItemsGroup(menuCategory.getStringField("name"), label);

            List<Entity> menuItems = getDataDefinition("item").find().add(SearchRestrictions.belongsTo("category", menuCategory))
                    .addOrder(SearchOrders.asc("succession")).list().getEntities();

            for (Entity menuItem : menuItems) {
                if (!(Boolean) menuItem.getField("active")) {
                    continue;
                }

                Entity menuView = menuItem.getBelongsToField("view");

                String itemLabel = menuItem.getStringField("name");

                if (menuItem.getStringField("pluginIdentifier") != null) {
                    itemLabel = translationUtilsService.getItemTranslation(menuItem, locale);
                }

                if (menuView.getStringField("url") != null) {
                    if (canAccess(menuView.getStringField("pluginIdentifier"), menuView.getStringField("name"))) {
                        category.addItem(new UrlMenuItem(menuItem.getStringField("name"), itemLabel, null, menuView
                                .getStringField("url")));
                    }

                } else if (canAccess(menuView.getStringField("pluginIdentifier"), menuView.getStringField("name"))) {
                    category.addItem(new ViewDefinitionMenuItemItem(menuItem.getStringField("name"), itemLabel, menuView
                            .getStringField("pluginIdentifier"), menuView.getStringField("name")));
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

        menuView = getDataDefinition("view").create();
        menuView.setField("pluginIdentifier", pluginIdentifier);
        menuView.setField("name", viewName);
        menuView.setField("url", url);
        menuView.setField("view", view);
        getDataDefinition("view").save(menuView);
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
            getDataDefinition("item").delete(itemToThisView.getId());
        }

        getDataDefinition("view").delete(menuView.getId());
    }

    @Override
    @Transactional
    public void createCategory(final String pluginIdentifier, final String categoryName) {
        Entity menuCategory = getCategory(pluginIdentifier, categoryName);

        if (menuCategory != null) {
            return;
        }

        menuCategory = getDataDefinition("category").create();
        menuCategory.setField("pluginIdentifier", pluginIdentifier);
        menuCategory.setField("name", categoryName);
        menuCategory.setField("accessible", true);
        menuCategory.setField("succession", getTotalNumberOfCategories());
        getDataDefinition("category").save(menuCategory);
    }

    @Override
    @Transactional
    public void removeCategory(final String pluginIdentifier, final String categoryName) {
        Entity menuCategory = getCategory(pluginIdentifier, categoryName);

        if (menuCategory == null) {
            return;
        }

        if (menuCategory.getHasManyField("items").size() == 0) {
            getDataDefinition("category").delete(menuCategory.getId());
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
            menuItem = getDataDefinition("item").create();
            menuItem.setField("pluginIdentifier", pluginIdentifier);
            menuItem.setField("name", name);
            menuItem.setField("active", true);
        }

        if (menuItem == null || !menuView.equals(menuItem.getField("view"))) {
            menuItem.setField("view", menuView);
            menuItem.setField("category", menuCategory);
            menuItem.setField("succession", getTotalNumberOfItems(menuCategory));
            getDataDefinition("item").save(menuItem);
        }
    }

    @Override
    @Transactional
    public void removeItem(final String pluginIdentifier, final String name) {
        Entity menuItem = getItem(pluginIdentifier, name);
        if (menuItem == null) {
            return;
        }
        getDataDefinition("item").delete(menuItem.getId());
    }

    private DataDefinition getDataDefinition(final String entityName) {
        return dataDefinitionService.get("qcadooView", entityName);
    }

    private int getTotalNumberOfItems(final Entity category) {
        return getDataDefinition("item").find().add(SearchRestrictions.belongsTo("category", category)).list()
                .getTotalNumberOfEntities() + 1;
    }

    private int getTotalNumberOfCategories() {
        return getDataDefinition("category").find().list().getTotalNumberOfEntities() + 1;
    }

    private Entity getCategory(final String pluginIdentifier, final String categoryName) {
        return getDataDefinition("category").find().add(SearchRestrictions.eq("name", categoryName))
                .add(SearchRestrictions.eq("pluginIdentifier", pluginIdentifier)).setMaxResults(1).uniqueResult();
    }

    private Entity getCategory(final String categoryName) {
        return getDataDefinition("category").find().add(SearchRestrictions.eq("name", categoryName)).setMaxResults(1)
                .uniqueResult();
    }

    private Entity getItem(final String pluginIdentifier, final String itemName) {
        return getDataDefinition("item").find().add(SearchRestrictions.eq("name", itemName))
                .add(SearchRestrictions.eq("pluginIdentifier", pluginIdentifier)).setMaxResults(1).uniqueResult();
    }

    private Collection<Entity> getItemsToView(final Entity view) {
        return getDataDefinition("item").find().add(SearchRestrictions.belongsTo("view", view)).list().getEntities();
    }

    private Entity getView(final String pluginIdentifier, final String viewName) {
        return getDataDefinition("view").find().add(SearchRestrictions.eq("name", viewName))
                .add(SearchRestrictions.eq("pluginIdentifier", pluginIdentifier)).setMaxResults(1).uniqueResult();
    }

}
