/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.4
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
package com.qcadoo.plugins.dictionaries.internal.hooks;

import static com.qcadoo.model.constants.DictionaryItemFields.TECHNICAL_CODE;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.constants.QcadooModelConstants;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.FieldComponent;
import com.qcadoo.view.api.components.FormComponent;
import com.qcadoo.view.api.components.WindowComponent;
import com.qcadoo.view.api.ribbon.RibbonActionItem;

@Service
public class DictionaryItemDetailsHooks {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    public void blockedActivationOptionWhenDictionaryWasAddFromSystem(final ViewDefinitionState view) {
        FormComponent form = (FormComponent) view.getComponentByReference("form");
        if (form.getEntityId() == null) {
            return;
        }
        Entity dictionaryItem = form.getEntity();
        if (StringUtils.isEmpty(dictionaryItem.getStringField(TECHNICAL_CODE))) {
            changedEnabledButton(view, false);
        }
    }

    protected void changedEnabledButton(final ViewDefinitionState view, final boolean enabled) {
        WindowComponent window = (WindowComponent) view.getComponentByReference("window");
        RibbonActionItem deactivateButton = window.getRibbon().getGroupByName("states").getItemByName("deactivate");
        deactivateButton.setEnabled(enabled);
        deactivateButton.requestUpdate(true);
        RibbonActionItem activateButton = window.getRibbon().getGroupByName("states").getItemByName("activate");
        activateButton.setEnabled(enabled);
        activateButton.requestUpdate(true);
    }

    public void disableNameEdit(final ViewDefinitionState view) {
        FormComponent form = (FormComponent) view.getComponentByReference("form");
        if (form.getEntityId() == null) {
            return;
        } else {
            FieldComponent nameFieldComponent = (FieldComponent) view.getComponentByReference("name");
            nameFieldComponent.setEnabled(false);
        }
    }

    public void disableDictionaryItemFormForExternalItems(final ViewDefinitionState state) {
        FormComponent form = (FormComponent) state.getComponentByReference("form");

        if (form.getEntityId() == null) {
            form.setFormEnabled(true);
            return;
        }

        Entity entity = dataDefinitionService.get(QcadooModelConstants.PLUGIN_IDENTIFIER,
                QcadooModelConstants.MODEL_DICTIONARY_ITEM).get(form.getEntityId());

        if (entity == null) {
            return;
        }

        String externalNumber = entity.getStringField("externalNumber");

        if (externalNumber != null) {
            form.setFormEnabled(false);
        } else {
            form.setFormEnabled(true);
        }
    }
}
