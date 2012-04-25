/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.5
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
package com.qcadoo.view.internal.components.window;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.view.api.components.WindowComponent;
import com.qcadoo.view.api.ribbon.Ribbon;
import com.qcadoo.view.internal.api.InternalComponentState;
import com.qcadoo.view.internal.ribbon.RibbonUtils;
import com.qcadoo.view.internal.ribbon.model.InternalRibbon;
import com.qcadoo.view.internal.states.AbstractContainerState;

public class WindowComponentState extends AbstractContainerState implements WindowComponent {

    private final InternalRibbon ribbon;

    private final WindowComponentPattern pattern;

    public static final String JSON_ACTIVE_MENU = "activeMenu";

    private String activeMenu = null;

    public WindowComponentState(final WindowComponentPattern pattern) {
        this.pattern = pattern;
        ribbon = pattern.getRibbon().getCopy();
    }

    @Override
    protected void initializeContent(final JSONObject json) throws JSONException {
        requestRender();
    }

    @Override
    protected JSONObject renderContent() throws JSONException {
        JSONObject json = new JSONObject();

        List<String> errorTabs = new LinkedList<String>();
        for (Map.Entry<String, InternalComponentState> child : getChildren().entrySet()) {
            if (child.getValue().isHasError()) {
                errorTabs.add(child.getKey());
            }
        }
        JSONArray errors = new JSONArray();
        for (String tabName : errorTabs) {
            errors.put(tabName);
        }
        json.put("errors", errors);

        if (pattern.getContextualHelpUrl() != null) {
            json.put("contextualHelpUrl", pattern.getContextualHelpUrl());
        }

        if (ribbon != null) {
            InternalRibbon diffrenceRibbon = ribbon.getUpdate();
            if (diffrenceRibbon != null) {
                json.put("ribbon", RibbonUtils.translateRibbon(diffrenceRibbon, getLocale(), pattern));
            }
        }

        if (activeMenu != null) {
            json.put(JSON_ACTIVE_MENU, activeMenu);
        }

        return json;
    }

    @Override
    public void initializeContext(final JSONObject json) throws JSONException {
        if (json.has(JSON_ACTIVE_MENU) && !json.isNull(JSON_ACTIVE_MENU)) {
            activeMenu = json.getString(JSON_ACTIVE_MENU);
        }
    }

    @Override
    public Ribbon getRibbon() {
        return ribbon;
    }

    @Override
    public void requestRibbonRender() {
        requestRender();
    }

}
