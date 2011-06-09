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
