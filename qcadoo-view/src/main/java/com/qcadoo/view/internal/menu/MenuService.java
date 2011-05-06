package com.qcadoo.view.internal.menu;

import java.util.Locale;


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

}