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
import org.springframework.beans.BeansException;

import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.components.ganttChart.GanttChartItemResolver;
import com.qcadoo.view.internal.ComponentDefinition;
import com.qcadoo.view.internal.ComponentOption;
import com.qcadoo.view.internal.components.ganttChart.GanttChartScaleImpl.ZoomLevel;
import com.qcadoo.view.internal.patterns.AbstractComponentPattern;

public class GanttChartComponentPattern extends AbstractComponentPattern {

    private static final String JS_OBJECT = "QCD.components.elements.GanttChart";

    private static final String JSP_PATH = "elements/ganttChart.jsp";

    private GanttChartItemResolver resolver;

    private int defaultStartDay = 0;

    private int defaultEndDay = 21;

    private ZoomLevel defaultZoomLevel = ZoomLevel.H3;

    public GanttChartComponentPattern(final ComponentDefinition componentDefinition) {
        super(componentDefinition);
    }

    @Override
    protected ComponentState getComponentStateInstance() {
        return new GanttChartComponentState(resolver, defaultZoomLevel, defaultStartDay, defaultEndDay);
    }

    @Override
    protected void initializeComponent() throws JSONException {
        for (ComponentOption option : getOptions()) {
            if ("resolver".equals(option.getType())) {
                try {
                    resolver = (GanttChartItemResolver) getApplicationContext().getBean(
                            Thread.currentThread().getContextClassLoader().loadClass(option.getType()));
                } catch (BeansException e) {
                    throw new IllegalStateException("Gantt can't find resolver " + option.getType(), e);
                } catch (ClassNotFoundException e) {
                    throw new IllegalStateException("Gantt can't find resolver " + option.getType(), e);
                }
            } else if ("defaultZoomLevel".equals(option.getType())) {
                defaultZoomLevel = ZoomLevel.valueOf(option.getValue());
            } else if ("defaultStartDay".equals(option.getType())) {
                defaultStartDay = Integer.valueOf(option.getValue());
            } else if ("defaultEndDay".equals(option.getType())) {
                defaultEndDay = Integer.valueOf(option.getValue());
            }
        }
        if (resolver == null) {
            throw new IllegalStateException("Gantt must contain 'resolver' option");
        }
    }

    @Override
    protected JSONObject getJsOptions(final Locale locale) throws JSONException {
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
