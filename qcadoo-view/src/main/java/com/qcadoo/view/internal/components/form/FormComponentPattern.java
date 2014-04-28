/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.2.0
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
package com.qcadoo.view.internal.components.form;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;

import com.qcadoo.security.api.SecurityRole;
import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.internal.ComponentDefinition;
import com.qcadoo.view.internal.ComponentOption;
import com.qcadoo.view.internal.patterns.AbstractContainerPattern;
import com.qcadoo.view.internal.xml.ViewDefinitionParser;
import com.qcadoo.view.internal.xml.ViewDefinitionParserNodeException;

public class FormComponentPattern extends AbstractContainerPattern {

    private static final String JSP_PATH = "containers/form.jsp";

    private static final String JS_OBJECT = "QCD.components.containers.Form";

    private boolean header;

    private String expressionEdit = "#id";

    private String expressionNew;

    private SecurityRole authorizationRole;

    public FormComponentPattern(final ComponentDefinition componentDefinition) {
        super(componentDefinition);
    }

    @Override
    protected void initializeComponent() throws JSONException {
        for (ComponentOption option : getOptions()) {
            if ("expression".equals(option.getType())) {
                expressionEdit = option.getValue();
            } else if ("expressionNew".equals(option.getType())) {
                expressionNew = option.getValue();
            } else if ("header".equals(option.getType())) {
                header = Boolean.parseBoolean(option.getValue());
            } else {
                throw new IllegalStateException("Unknown option for form: " + option.getType());
            }
        }
    }

    @Override
    protected JSONObject getJsOptions(final Locale locale) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("header", header);

        JSONObject translations = new JSONObject();

        addTranslation(translations, "confirmCancelMessage", locale);
        addTranslation(translations, "confirmDeleteMessage", locale);
        addTranslation(translations, "entityWithoutIdentifier", locale);
        addTranslation(translations, "noEntity", locale);

        translations.put("loading", getTranslationService().translate("qcadooView.loading", locale));

        json.put("translations", translations);

        return json;
    }

    private void addTranslation(final JSONObject translation, final String key, final Locale locale) throws JSONException {
        translation.put(key, getTranslationService()
                .translate(getTranslationPath() + "." + key, "qcadooView.form." + key, locale));
    }

    @Override
    public void parse(final Node componentNode, final ViewDefinitionParser parser) throws ViewDefinitionParserNodeException {
        super.parse(componentNode, parser);
        authorizationRole = parser.getAuthorizationRole(componentNode);
    }

    @Override
    public ComponentState getComponentStateInstance() {
        return new FormComponentState(this);
    }

    @Override
    public String getJspFilePath() {
        return JSP_PATH;
    }

    @Override
    public String getJsFilePath() {
        return JS_PATH;
    }

    @Override
    public String getJsObjectName() {
        return JS_OBJECT;
    }

    public String getExpressionEdit() {
        return expressionEdit;
    }

    public String getExpressionNew() {
        return expressionNew;
    }

    public SecurityRole getAuthorizationRole() {
        return authorizationRole;
    }

}
