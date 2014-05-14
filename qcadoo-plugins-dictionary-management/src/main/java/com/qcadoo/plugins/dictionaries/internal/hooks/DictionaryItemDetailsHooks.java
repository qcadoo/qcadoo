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
package com.qcadoo.plugins.dictionaries.internal.hooks;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.constants.QcadooModelConstants;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.FormComponent;
import com.qcadoo.view.api.components.WindowComponent;
import com.qcadoo.view.api.ribbon.RibbonActionItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.qcadoo.model.constants.DictionaryItemFields.TECHNICAL_CODE;

@Service
public class DictionaryItemDetailsHooks {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    public void blockedDeleteOptionWhenDictionaryWasAddFromSystem(final ViewDefinitionState view) {
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
        RibbonActionItem deleteButton = window.getRibbon().getGroupByName("actions").getItemByName("delete");
        deleteButton.setEnabled(enabled);
        deleteButton.requestUpdate(true);
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
