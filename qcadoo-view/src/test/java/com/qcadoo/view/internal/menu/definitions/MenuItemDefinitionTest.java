package com.qcadoo.view.internal.menu.definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class MenuItemDefinitionTest {

    private static final String ITEM_PLUGIN = "itemPlugin";

    private static final String ITEM_NAME = "itemName";

    private static final String CATEGORY_NAME = "categoryName";

    private static final String ROLE_IDENTIFIER = "roleIdentifier";

    private static final String VIEW_PLUGIN = "viewPlugin";

    private static final String VIEW_NAME = "viewName";

    private static final String VIEW_URL = "viewUrl";

    @Test
    public final void shouldMenuItemDefinition() {
        // when
        MenuItemDefinition itemDefinition = MenuItemDefinition.create(ITEM_PLUGIN, ITEM_NAME, CATEGORY_NAME, ROLE_IDENTIFIER);

        // then
        assertEquals(ITEM_PLUGIN, itemDefinition.getPluginIdentifier());
        assertEquals(ITEM_NAME, itemDefinition.getName());
        assertEquals(CATEGORY_NAME, itemDefinition.getCategoryName());
        assertEquals(ROLE_IDENTIFIER, itemDefinition.getAuthRoleIdentifier());

        assertNull(itemDefinition.getViewPluginIdentifier());
        assertNull(itemDefinition.getViewName());
        assertNull(itemDefinition.getUrl());
    }

    @Test
    public final void shouldCreateMenuViewItemDefinition() {
        // when
        MenuItemDefinition itemDefinition = MenuItemDefinition.create(ITEM_PLUGIN, ITEM_NAME, CATEGORY_NAME, ROLE_IDENTIFIER)
                .forView(VIEW_PLUGIN, VIEW_NAME);

        // then
        assertEquals(ITEM_PLUGIN, itemDefinition.getPluginIdentifier());
        assertEquals(ITEM_NAME, itemDefinition.getName());
        assertEquals(CATEGORY_NAME, itemDefinition.getCategoryName());
        assertEquals(ROLE_IDENTIFIER, itemDefinition.getAuthRoleIdentifier());

        assertEquals(VIEW_PLUGIN, itemDefinition.getViewPluginIdentifier());
        assertEquals(VIEW_NAME, itemDefinition.getViewName());
        assertNull(itemDefinition.getUrl());
    }

    @Test
    public final void shouldMenuUrlItemDefinition() {
        // when
        MenuItemDefinition itemDefinition = MenuItemDefinition.create(ITEM_PLUGIN, ITEM_NAME, CATEGORY_NAME, ROLE_IDENTIFIER)
                .forUrl(VIEW_URL);

        // then
        assertEquals(ITEM_PLUGIN, itemDefinition.getPluginIdentifier());
        assertEquals(ITEM_NAME, itemDefinition.getName());
        assertEquals(CATEGORY_NAME, itemDefinition.getCategoryName());
        assertEquals(ROLE_IDENTIFIER, itemDefinition.getAuthRoleIdentifier());

        assertEquals(ITEM_PLUGIN, itemDefinition.getViewPluginIdentifier());
        assertEquals(ITEM_NAME, itemDefinition.getViewName());
        assertEquals(VIEW_URL, itemDefinition.getUrl());
    }

}
