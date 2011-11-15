/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.10
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
package com.qcadoo.view.internal.components;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.model.api.types.BelongsToType;
import com.qcadoo.view.internal.ComponentDefinition;
import com.qcadoo.view.internal.ComponentOption;
import com.qcadoo.view.internal.patterns.AbstractComponentPattern;

public abstract class FieldComponentPattern extends AbstractComponentPattern {

    private int labelWidth = 30;

    private boolean defaultRequired = false;

    public FieldComponentPattern(final ComponentDefinition componentDefinition) {
        super(componentDefinition);

    }

    @Override
    protected void initializeComponent() throws JSONException {
        for (ComponentOption option : getOptions()) {
            if ("labelWidth".equals(option.getType())) {
                labelWidth = Integer.parseInt(option.getValue());
            } else if ("required".equals(option.getType())) {
                defaultRequired = Boolean.parseBoolean(option.getValue());
            }
        }
    }

    @Override
    protected Map<String, Object> getJspOptions(final Locale locale) {
        Map<String, Object> options = new HashMap<String, Object>();
        Map<String, Object> translations = new HashMap<String, Object>();

        if (getFieldDefinition() == null) {
            translations.put("label", getTranslationService().translate(getTranslationPath() + ".label", locale));
        } else {
            String code1 = getFieldDefinition().getDataDefinition().getPluginIdentifier() + "."
                    + getFieldDefinition().getDataDefinition().getName() + "." + getFieldDefinition().getName() + ".label";

            if (BelongsToType.class.isAssignableFrom(getFieldDefinition().getType().getClass())) {
                DataDefinition fieldDataDefinition = ((BelongsToType) getFieldDefinition().getType()).getDataDefinition();
                String code2 = fieldDataDefinition.getPluginIdentifier() + "." + fieldDataDefinition.getName() + "."
                        + getFieldDefinition().getName() + ".label";
                translations.put("label", getTranslationService()
                        .translate(getTranslationPath() + ".label", code1, code2, locale));
            } else {
                translations.put("label", getTranslationService().translate(getTranslationPath() + ".label", code1, locale));
            }
        }

        if (isHasDescription()) {
            translations.put("description", getTranslationService().translate(getTranslationPath() + ".description", locale));
            translations.put(
                    "descriptionHeader",
                    getTranslationService().translate(getTranslationPath() + "." + getPath() + ".descriptionHeader",
                            "qcadooView.form.descriptionHeader", locale));
        }

        options.put("translations", translations);
        options.put("labelWidth", labelWidth);

        return options;
    }

    public FieldDefinition getFieldComponentFieldDefinition() {
        return getFieldDefinition();
    }

    public boolean isRequired() {
        if (getFieldDefinition() != null) {
            return getFieldDefinition().isRequired() || defaultRequired;
        } else {
            return defaultRequired;
        }
    }
}
