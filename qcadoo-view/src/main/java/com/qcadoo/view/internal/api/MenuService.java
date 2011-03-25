package com.qcadoo.view.internal.api;

import java.util.Locale;

import com.qcadoo.view.internal.menu.MenuDefinition;

public interface MenuService {

    MenuDefinition getMenu(final Locale locale);

    void createViewIfNotExists(String pluginIdentifier, String viewName, String view, String url);

    void enableView(String pluginIdentifier, String viewName);

    void disableView(String pluginIdentifier, String viewName);

    void createCategoryIfNotExists(String pluginIdentifier, String categoryName);

    void createItemIfNotExists(String pluginIdentifier, String name, String category, String viewPluginIdentifier, String viewName);

    void enableItem(String pluginIdentifier, String name);

    void disableItem(String pluginIdentifier, String name);

}
