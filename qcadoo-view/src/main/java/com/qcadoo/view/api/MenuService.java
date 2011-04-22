package com.qcadoo.view.api;

import java.util.Locale;

import com.qcadoo.model.api.Entity;
import com.qcadoo.view.api.menu.MenuDefinition;

/**
 * Service to access menu information
 * 
 * @since 0.4.0
 */
public interface MenuService {

    /**
     * Returns menu for current user and localization
     * 
     * @param locale
     *            localization
     * @return menu definition for current user
     */
    MenuDefinition getMenu(final Locale locale);

    String getCategoryTranslation(final Entity category, final Locale locale);

    String getItemTranslation(final Entity item, final Locale locale);

}