package com.qcadoo.view.api;

import java.util.Locale;

import com.qcadoo.view.internal.menu.MenuDefinition;

public interface MenuService {

    MenuDefinition getMenu(final Locale locale);

}