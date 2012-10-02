/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.7
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
package com.qcadoo.plugins.customTranslations.internal.listeners;

import static com.qcadoo.customTranslation.constants.CustomTranslationFields.CUSTOM_TRANSLATION;
import static com.qcadoo.customTranslation.constants.CustomTranslationFields.LOCALE;
import static com.qcadoo.plugins.customTranslations.constants.CustomTranslationFieldsCTM.PROPERTIES_TRANSLATION;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.customTranslation.api.CustomTranslationManagementService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ComponentState.MessageType;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.FieldComponent;
import com.qcadoo.view.api.components.FormComponent;

@Service
public class ReplaceCustomTranslationsListeners {

    private static final String L_FORM = "form";

    private static final String L_REPLACE_TO = "replaceTo";

    private static final String L_REPLACE_FROM = "replaceFrom";

    @Autowired
    private CustomTranslationManagementService customTranslationManagementService;

    public void replaceCustomTranslations(final ViewDefinitionState view, final ComponentState state, final String[] args) {
        FormComponent replaceCustomTranslationsFrom = (FormComponent) view.getComponentByReference(L_FORM);

        FieldComponent localeField = (FieldComponent) view.getComponentByReference(LOCALE);
        FieldComponent replaceFromField = (FieldComponent) view.getComponentByReference(L_REPLACE_FROM);
        FieldComponent replaceToField = (FieldComponent) view.getComponentByReference(L_REPLACE_TO);

        String locale = (String) localeField.getFieldValue();
        String replaceFrom = (String) replaceFromField.getFieldValue();
        String replaceTo = (String) replaceToField.getFieldValue();

        state.performEvent(view, "refresh");

        if (StringUtils.isEmpty(replaceFrom) || StringUtils.isEmpty(replaceTo)) {
            replaceCustomTranslationsFrom.addMessage(
                    "qcadooCustomTranslations.replaceCustomTranslations.message.replaceCustomTranslationsFailure",
                    MessageType.FAILURE);
        } else {
            List<Entity> customTranslations = customTranslationManagementService.getCustomTranslations(locale);

            if (customTranslations == null) {
                replaceCustomTranslationsFrom.addMessage(
                        "qcadooCustomTranslations.replaceCustomTranslations.message.replaceCustomTranslationsEmpty",
                        MessageType.INFO);
            } else {
                int count = 0;
                for (Entity customTranslation : customTranslations) {
                    String translation = customTranslation.getStringField(PROPERTIES_TRANSLATION);

                    if (translation.contains(replaceFrom)) {
                        count++;

                        translation = translation.replace(replaceFrom, replaceTo);

                        customTranslation.setField(CUSTOM_TRANSLATION, translation);

                        customTranslation.getDataDefinition().save(customTranslation);
                    }
                }

                if (count > 0) {
                    replaceCustomTranslationsFrom.addMessage(
                            "qcadooCustomTranslations.replaceCustomTranslations.message.replaceCustomTranslationsSuccess",
                            MessageType.SUCCESS);
                } else {
                    replaceCustomTranslationsFrom.addMessage(
                            "qcadooCustomTranslations.replaceCustomTranslations.message.replaceCustomTranslationsInfo",
                            MessageType.INFO);
                }
            }
        }
    }

}
