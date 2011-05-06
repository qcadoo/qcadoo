package com.qcadoo.view.internal.api;

import com.qcadoo.view.internal.menu.MenuService;

public interface InternalMenuService extends MenuService {

    void addView(String pluginIdentifier, String viewName, String view, String url);

    void removeView(String pluginIdentifier, String viewName);

    void createCategory(String pluginIdentifier, String categoryName);

    void removeCategory(String pluginIdentifier, String categoryName);

    void createItem(String pluginIdentifier, String name, String category, String viewPluginIdentifier, String viewName);

    void removeItem(String pluginIdentifier, String name);

}
