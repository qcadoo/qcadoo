/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.2
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
package com.qcadoo.view.internal.components.ganttChart;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.internal.ComponentDefinition;
import com.qcadoo.view.internal.patterns.AbstractComponentPattern;

public class GanttChartComponentPattern extends AbstractComponentPattern {

    private static final String JS_OBJECT = "QCD.components.elements.GanttChart";

    private static final String JSP_PATH = "elements/ganttChart.jsp";

    public GanttChartComponentPattern(ComponentDefinition componentDefinition) {
        super(componentDefinition);
    }

    @Override
    protected ComponentState getComponentStateInstance() {
        return new GanttChartComponentState();
    }

    @Override
    protected JSONObject getJsOptions(Locale locale) throws JSONException {
        JSONObject translations = new JSONObject();
        addTranslation(translations, "header.label", locale);
        addTranslation(translations, "header.dateFrom", locale);
        addTranslation(translations, "header.dateTo", locale);
        addTranslation(translations, "header.zoom1h", locale);
        addTranslation(translations, "header.zoom3h", locale);
        addTranslation(translations, "header.zoom6h", locale);
        addTranslation(translations, "header.zoom1d", locale);

        addTranslation(translations, "description.dateFrom", locale);
        addTranslation(translations, "description.dateTo", locale);

        JSONObject json = super.getJsOptions(locale);
        json.put("translations", translations);
        return json;
    }

    private void addTranslation(final JSONObject translation, final String key, final Locale locale) throws JSONException {
        translation.put(key,
                getTranslationService().translate(getTranslationPath() + "." + key, "qcadooView.gantt." + key, locale));
    }

    @Override
    protected String getJspFilePath() {
        return JSP_PATH;
    }

    @Override
    protected String getJsFilePath() {
        return JS_PATH;
    }

    @Override
    protected String getJsObjectName() {
        return JS_OBJECT;
    }

}
