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

    /**
     * Returns category translation
     * 
     * @param category
     *            category entity
     * @param locale
     *            localization
     * @return category translation
     */
    String getCategoryTranslation(final Entity category, final Locale locale);

    /**
     * Returns item translation
     * 
     * @param item
     *            item entity
     * @param locale
     *            localization
     * @return item translation
     */
    String getItemTranslation(final Entity item, final Locale locale);

}