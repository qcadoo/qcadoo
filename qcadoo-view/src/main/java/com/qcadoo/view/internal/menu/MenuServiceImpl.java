/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo MES
 * Version: 0.3.0
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

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.aop.Monitorable;
import com.qcadoo.model.api.search.Restrictions;
import com.qcadoo.plugin.api.PluginAccessor;
import com.qcadoo.plugin.api.PluginUtil;
import com.qcadoo.security.api.SecurityRole;
import com.qcadoo.security.api.SecurityRolesService;
import com.qcadoo.security.api.SecurityViewDefinitionRoleResolver;
import com.qcadoo.view.api.menu.MenuDefinition;
import com.qcadoo.view.api.menu.MenulItemsGroup;
import com.qcadoo.view.internal.api.InternalMenuService;
import com.qcadoo.view.internal.menu.items.UrlMenuItem;
import com.qcadoo.view.internal.menu.items.ViewDefinitionMenuItemItem;

@Service
public final class MenuServiceImpl implements InternalMenuService {

    @Autowired
    private PluginAccessor pluginAccessor;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Value("${setAsDemoEnviroment}")
    private boolean setAsDemoEnviroment;

    @Autowired
    private SecurityRolesService securityRolesService;

    @Autowired
    private SecurityViewDefinitionRoleResolver viewDefinitionRoleResolver;

    @Override
    @Transactional(readOnly = true)
    @Monitorable
    public MenuDefinition getMenu(final Locale locale) {

        MenuDefinition menuDefinition = new MenuDefinition();

        List<Entity> menuCategories = dataDefinitionService.get("qcadooView", "category").find().setOrderAscBy("succession")
                .list().getEntities();

        // MenulItemsGroup administrationCategory = null;

        // boolean hasMenuManagement = false;

        for (Entity menuCategory : menuCategories) {
            String label = menuCategory.getStringField("name");

            if (menuCategory.getStringField("pluginIdentifier") != null) {
                label = translationService.translate(
                        menuCategory.getStringField("pluginIdentifier") + ".menu." + menuCategory.getStringField("name"), locale);
            }

            MenulItemsGroup category = new MenulItemsGroup(menuCategory.getStringField("name"), label);

            List<Entity> menuItems = dataDefinitionService
                    .get("qcadooView", "item")
                    .find()
                    .addRestriction(
                            Restrictions.belongsTo(dataDefinitionService.get("qcadooView", "item").getField("category"),
                                    menuCategory)).setOrderAscBy("succession").list().getEntities();

            for (Entity menuItem : menuItems) {
                if (!(Boolean) menuItem.getField("active")) {
                    continue;
                }

                Entity menuView = menuItem.getBelongsToField("view");

                String itemLabel = menuItem.getStringField("name");

                if (menuItem.getStringField("pluginIdentifier") != null) {
                    itemLabel = translationService.translate(menuItem.getStringField("pluginIdentifier") + ".menu."
                            + menuCategory.getStringField("name") + "." + menuItem.getStringField("name"), locale);
                }

                if (menuView.getStringField("url") != null) {
                    category.addItem(new UrlMenuItem(menuItem.getStringField("name"), itemLabel, null, menuView
                            .getStringField("url")));

                } else if (canAccess(menuView.getStringField("pluginIdentifier"), menuView.getStringField("name"))) {
                    category.addItem(new ViewDefinitionMenuItemItem(menuItem.getStringField("name"), itemLabel, menuView
                            .getStringField("pluginIdentifier"), menuView.getStringField("name")));
                }

                // if ("menu".equals(menuView.getStringField("pluginIdentifier"))
                // && "menuCategories".equals(menuView.getStringField("view"))) {
                // hasMenuManagement = true;
                // }
            }

            // if ("administration".equals(menuCategory.getStringField("name"))) {
            // administrationCategory = category;
            // } else if (!category.getItems().isEmpty()) {
            // menuDefinition.addItem(category);
            // }

            if (!category.getItems().isEmpty()) {
                menuDefinition.addItem(category);
            }
        }

        // if (!setAsDemoEnviroment) {
        // if (!hasMenuManagement && pluginAccessor.getEnabledPlugin("menu") != null) {
        // if (administrationCategory == null) {
        // administrationCategory = new MenulItemsGroup("administration", translationService.translate(
        // "basic.menu.administration", locale));
        // }
        // administrationCategory.addItem(new ViewDefinitionMenuItemItem("menuCategories", translationService.translate(
        // "menu.menu.administration.menu", locale), "menu", "menuCategories"));
        // }
        //
        // if (administrationCategory != null) {
        // menuDefinition.addItem(administrationCategory);
        // }
        // }

        return menuDefinition;
    }

    private boolean canAccess(final String pluginIdentifier, final String viewName) {
        if (!PluginUtil.isEnabled(pluginIdentifier)) {
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
    public void createViewIfNotExists(final String pluginIdentifier, final String viewName, final String view, final String url) {
        Entity menuView = getView(pluginIdentifier, viewName);

        if (menuView != null) {
            return;
        }

        menuView = dataDefinitionService.get("qcadooView", "view").create();
        menuView.setField("pluginIdentifier", pluginIdentifier);
        menuView.setField("name", viewName);
        menuView.setField("url", url);
        menuView.setField("view", view);
        dataDefinitionService.get("qcadooView", "view").save(menuView);
    }

    @Override
    @Transactional
    public void enableView(final String pluginIdentifier, final String viewName) {
        // ignore

    }

    @Override
    @Transactional
    public void disableView(final String pluginIdentifier, final String viewName) {
        // ignore
    }

    @Override
    @Transactional
    public void createCategoryIfNotExists(final String pluginIdentifier, final String categoryName) {
        Entity menuCategory = getCategory(pluginIdentifier, categoryName);

        if (menuCategory != null) {
            return;
        }

        menuCategory = dataDefinitionService.get("qcadooView", "category").create();
        menuCategory.setField("pluginIdentifier", pluginIdentifier);
        menuCategory.setField("name", categoryName);
        menuCategory.setField("succession", getTotalNumberOfCategories());
        dataDefinitionService.get("qcadooView", "category").save(menuCategory);
    }

    @Override
    @Transactional
    public void createItemIfNotExists(final String pluginIdentifier, final String name, final String category,
            final String viewPluginIdentifier, final String viewName) {
        Entity menuItem = getItem(pluginIdentifier, name);

        if (menuItem != null) {
            return;
        }

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

        menuItem = dataDefinitionService.get("qcadooView", "item").create();
        menuItem.setField("pluginIdentifier", pluginIdentifier);
        menuItem.setField("name", name);
        menuItem.setField("active", true);
        menuItem.setField("category", menuCategory);
        menuItem.setField("view", menuView);
        menuItem.setField("succession", getTotalNumberOfItems(menuCategory));
        dataDefinitionService.get("qcadooView", "item").save(menuItem);
    }

    @Override
    @Transactional
    public void enableItem(final String pluginIdentifier, final String name) {
        Entity menuItem = getItem(pluginIdentifier, name);

        if (menuItem != null) {
            menuItem.setField("active", true);
            dataDefinitionService.get("qcadooView", "item").save(menuItem);
        }
    }

    @Override
    @Transactional
    public void disableItem(final String pluginIdentifier, final String name) {
        Entity menuItem = getItem(pluginIdentifier, name);

        if (menuItem != null) {
            menuItem.setField("active", false);
            dataDefinitionService.get("qcadooView", "item").save(menuItem);
        }
    }

    private int getTotalNumberOfItems(final Entity category) {
        return dataDefinitionService
                .get("qcadooView", "item")
                .find()
                .addRestriction(
                        Restrictions.belongsTo(dataDefinitionService.get("qcadooView", "item").getField("category"), category))
                .list().getTotalNumberOfEntities() + 1;
    }

    private int getTotalNumberOfCategories() {
        return dataDefinitionService.get("qcadooView", "category").find().list().getTotalNumberOfEntities() + 1;
    }

    private Entity getCategory(final String pluginIdentifier, final String categoryName) {
        return dataDefinitionService.get("qcadooView", "category").find().addRestriction(Restrictions.eq("name", categoryName))
                .addRestriction(Restrictions.eq("pluginIdentifier", pluginIdentifier)).setMaxResults(1).uniqueResult();
    }

    private Entity getCategory(final String categoryName) {
        return dataDefinitionService.get("qcadooView", "category").find().addRestriction(Restrictions.eq("name", categoryName))
                .setMaxResults(1).uniqueResult();
    }

    private Entity getItem(final String pluginIdentifier, final String itemName) {
        return dataDefinitionService.get("qcadooView", "item").find().addRestriction(Restrictions.eq("name", itemName))
                .addRestriction(Restrictions.eq("pluginIdentifier", pluginIdentifier)).setMaxResults(1).uniqueResult();
    }

    private Entity getView(final String pluginIdentifier, final String viewName) {
        return dataDefinitionService.get("qcadooView", "view").find().addRestriction(Restrictions.eq("name", viewName))
                .addRestriction(Restrictions.eq("pluginIdentifier", pluginIdentifier)).setMaxResults(1).uniqueResult();
    }

}
