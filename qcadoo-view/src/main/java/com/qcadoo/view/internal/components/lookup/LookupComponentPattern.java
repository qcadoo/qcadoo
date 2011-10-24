/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.9
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
package com.qcadoo.view.internal.components.lookup;

import static com.google.common.base.Preconditions.checkState;
import static org.springframework.util.StringUtils.hasText;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.types.HasManyType;
import com.qcadoo.model.internal.types.TreeEntitiesType;
import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ribbon.RibbonActionItem.Type;
import com.qcadoo.view.internal.ComponentDefinition;
import com.qcadoo.view.internal.ComponentOption;
import com.qcadoo.view.internal.api.InternalViewDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;
import com.qcadoo.view.internal.api.ViewDefinition;
import com.qcadoo.view.internal.components.FieldComponentPattern;
import com.qcadoo.view.internal.components.grid.GridComponentPattern;
import com.qcadoo.view.internal.components.window.WindowComponentPattern;
import com.qcadoo.view.internal.internal.ViewDefinitionImpl;
import com.qcadoo.view.internal.ribbon.model.InternalRibbon;
import com.qcadoo.view.internal.ribbon.model.InternalRibbonActionItem;
import com.qcadoo.view.internal.ribbon.model.InternalRibbonGroup;
import com.qcadoo.view.internal.ribbon.model.RibbonActionItemImpl;
import com.qcadoo.view.internal.ribbon.model.RibbonGroupImpl;
import com.qcadoo.view.internal.ribbon.model.RibbonImpl;
import com.qcadoo.view.internal.ribbon.model.SingleRibbonGroupPack;

public final class LookupComponentPattern extends FieldComponentPattern {

    private static final String JSP_PATH = "elements/lookup.jsp";

    private static final String JS_OBJECT = "QCD.components.elements.Lookup";

    private boolean textRepresentationOnDisabled;

    private String expression;

    private String fieldCode;

    private InternalViewDefinition lookupViewDefinition;

    public LookupComponentPattern(final ComponentDefinition componentDefinition) {
        super(componentDefinition);
    }

    @Override
    public ComponentState getComponentStateInstance() {
        if (getScopeFieldDefinition() == null) {
            return new LookupComponentState(null, fieldCode, expression, this);
        }

        String joinFieldName = null;
        if (TreeEntitiesType.class.isAssignableFrom(getScopeFieldDefinition().getType().getClass())) {
            joinFieldName = ((TreeEntitiesType) getScopeFieldDefinition().getType()).getJoinFieldName();
        } else {
            joinFieldName = ((HasManyType) getScopeFieldDefinition().getType()).getJoinFieldName();
        }
        FieldDefinition fieldDefinition = getDataDefinition().getField(joinFieldName);
        return new LookupComponentState(fieldDefinition, fieldCode, expression, this);
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

    @Override
    protected void initializeComponent() throws JSONException {
        super.initializeComponent();

        for (ComponentOption option : getOptions()) {
            if ("expression".equals(option.getType())) {
                expression = option.getValue();
            } else if ("fieldCode".equals(option.getType())) {
                fieldCode = option.getValue();
            } else if ("textRepresentationOnDisabled".equals(option.getType())) {
                textRepresentationOnDisabled = Boolean.parseBoolean(option.getValue());
            }
        }

        checkState(hasText(fieldCode), "Missing fieldCode for lookup");
        checkState(hasText(expression), "Missing expression for lookup");

        String viewName = getViewName();

        DataDefinition dataDefinition = getDataDefinition();

        if (getScopeFieldDefinition() != null) {
            dataDefinition = getScopeFieldDefinition().getDataDefinition();
        }

        lookupViewDefinition = new ViewDefinitionImpl(viewName, getViewDefinition().getPluginIdentifier(), dataDefinition, false,
                getTranslationService());

        WindowComponentPattern window = createWindowComponentPattern(lookupViewDefinition);

        GridComponentPattern grid = createGridComponentPattern(lookupViewDefinition, window);

        for (ComponentOption option : getOptions()) {
            if ("expression".equals(option.getType())) {
                continue;
            } else if ("fieldCode".equals(option.getType())) {
                continue;
            } else if ("textRepresentationOnDisabled".equals(option.getType())) {
                continue;
            } else if ("labelWidth".equals(option.getType())) {
                continue;
            } else if ("orderable".equals(option.getType())) {
                Map<String, String> newAttributes = new HashMap<String, String>();
                newAttributes.put("value", option.getValue() + ",lookupCode");
                option = new ComponentOption("orderable", newAttributes);
                grid.addOption(option);
            } else if ("searchable".equals(option.getType())) {
                Map<String, String> newAttributes = new HashMap<String, String>();
                newAttributes.put("value", option.getValue() + ",lookupCode");
                option = new ComponentOption("searchable", newAttributes);
                grid.addOption(option);
            } else {
                grid.addOption(option);
            }
        }

        grid.addOption(new ComponentOption("lookup", Collections.singletonMap("value", "true")));

        window.addChild(grid);

        lookupViewDefinition.addComponentPattern(window);
        lookupViewDefinition.initialize();
    }

    @Override
    protected JSONObject getJsOptions(final Locale locale) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("viewName", getViewName());

        JSONObject translations = new JSONObject();

        if (getFieldDefinition() == null) {
            translations.put("labelOnFocus", getTranslationService().translate(getTranslationPath() + ".label.focus", locale));
        } else {
            String code = getFieldDefinition().getDataDefinition().getPluginIdentifier() + "."
                    + getFieldDefinition().getDataDefinition().getName() + "." + getFieldDefinition().getName() + ".label.focus";
            translations.put("labelOnFocus",
                    getTranslationService().translate(getTranslationPath() + ".label.focus", code, locale));
        }

        translations.put(
                "noMatchError",
                getTranslationService().translate(getTranslationPath() + ".noMatchError", "qcadooView.lookup.noMatchError",
                        locale));
        translations.put(
                "moreTahnOneMatchError",
                getTranslationService().translate(getTranslationPath() + ".moreTahnOneMatchError",
                        "qcadooView.lookup.moreTahnOneMatchError", locale));
        translations.put(
                "noResultsInfo",
                getTranslationService().translate(getTranslationPath() + ".noResultsInfo", "qcadooView.lookup.noResultsInfo",
                        locale));
        translations.put(
                "tooManyResultsInfo",
                getTranslationService().translate(getTranslationPath() + ".tooManyResultsInfo",
                        "qcadooView.lookup.tooManyResultsInfo", locale));

        json.put("translations", translations);

        return json;
    }

    @Override
    protected Map<String, Object> getJspOptions(final Locale locale) {
        Map<String, Object> options = super.getJspOptions(locale);
        options.put("textRepresentationOnDisabled", textRepresentationOnDisabled);
        return options;
    }

    @Override
    protected void registerComponentViews(final InternalViewDefinitionService viewDefinitionService) {
        viewDefinitionService.save(lookupViewDefinition);
    }

    @Override
    protected void unregisterComponentViews(final InternalViewDefinitionService viewDefinitionService) {
        viewDefinitionService.delete(lookupViewDefinition);
    }

    private GridComponentPattern createGridComponentPattern(final ViewDefinition lookupViewDefinition,
            final WindowComponentPattern window) {
        ComponentDefinition gridComponentDefinition = new ComponentDefinition();
        gridComponentDefinition.setName("grid");
        gridComponentDefinition.setTranslationService(getTranslationService());
        gridComponentDefinition.setViewDefinition(lookupViewDefinition);
        gridComponentDefinition.setParent(window);
        gridComponentDefinition.setReference("grid");

        if (getScopeFieldDefinition() != null) {
            gridComponentDefinition.setSourceFieldPath(getScopeFieldDefinition().getName());
        }

        GridComponentPattern grid = new GridComponentPattern(gridComponentDefinition);
        grid.addOption(new ComponentOption("lookup", ImmutableMap.of("value", "true")));
        grid.addOption(new ComponentOption("fullscreen", ImmutableMap.of("value", "true")));
        grid.addOption(new ComponentOption("orderable", ImmutableMap.of("value", "lookupCode")));
        grid.addOption(new ComponentOption("order", ImmutableMap.of("column", "lookupCode", "direction", "asc")));
        grid.addOption(new ComponentOption("searchable", ImmutableMap.of("value", "lookupCode")));
        grid.addOption(createLookupValueColumn());
        grid.addOption(createLookupCodeColumn());

        return grid;
    }

    private ComponentOption createLookupValueColumn() {
        Map<String, String> valueColumnOptions = new HashMap<String, String>();
        valueColumnOptions.put("name", "lookupValue");
        valueColumnOptions.put("expression", expression);
        valueColumnOptions.put("hidden", "true");
        return new ComponentOption("column", valueColumnOptions);
    }

    private ComponentOption createLookupCodeColumn() {
        Map<String, String> codeVisibleColumnOptions = new HashMap<String, String>();
        codeVisibleColumnOptions.put("name", "lookupCode");
        codeVisibleColumnOptions.put("fields", fieldCode);
        codeVisibleColumnOptions.put("hidden", "false");
        codeVisibleColumnOptions.put("link", "true");
        return new ComponentOption("column", codeVisibleColumnOptions);
    }

    private WindowComponentPattern createWindowComponentPattern(final ViewDefinition lookupViewDefinition) {
        ComponentDefinition windowComponentDefinition = new ComponentDefinition();
        windowComponentDefinition.setName("window");
        windowComponentDefinition.setTranslationService(getTranslationService());
        windowComponentDefinition.setViewDefinition(lookupViewDefinition);

        WindowComponentPattern window = new WindowComponentPattern(windowComponentDefinition);
        window.setFixedHeight(true);
        window.setHeader(false);
        window.setRibbon(createRibbon());

        return window;
    }

    private InternalRibbon createRibbon() {
        InternalRibbonActionItem ribbonSelectActionItem = new RibbonActionItemImpl();
        ribbonSelectActionItem.setName("select");
        ribbonSelectActionItem.setIcon("acceptIcon24.png");
        ribbonSelectActionItem.setAction("#{window.grid}.performLinkClicked();");
        ribbonSelectActionItem.setType(Type.BIG_BUTTON);
        ribbonSelectActionItem.setEnabled(false);
        ribbonSelectActionItem.setMessage("#{translate(noRecordSelected)}");
        ribbonSelectActionItem
                .setScript("#{grid}.addOnChangeListener({onChange: function(selectedArray) {if (!selectedArray || selectedArray.length == 0) {"
                        + "this.disable('#{translate(noRecordSelected)}');} else {this.enable();}}});");

        InternalRibbonActionItem ribbonCancelActionItem = new RibbonActionItemImpl();
        ribbonCancelActionItem.setName("cancel");
        ribbonCancelActionItem.setIcon("cancelIcon24.png");
        ribbonCancelActionItem.setAction("#{window}.performCloseWindow");
        ribbonCancelActionItem.setType(Type.BIG_BUTTON);
        ribbonCancelActionItem.setEnabled(true);

        InternalRibbonGroup ribbonGroup = new RibbonGroupImpl("navigation");
        ribbonGroup.addItem(ribbonSelectActionItem);
        ribbonGroup.addItem(ribbonCancelActionItem);

        InternalRibbon ribbon = new RibbonImpl();
        ribbon.addGroupsPack(new SingleRibbonGroupPack(ribbonGroup));

        return ribbon;
    }

    private String getViewName() {
        return getViewDefinition().getName() + "." + getFunctionalPath() + ".lookup";
    }

}
