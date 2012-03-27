/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.4
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
package com.qcadoo.view.internal.ribbon;

import com.qcadoo.view.api.ribbon.RibbonActionItem;
import com.qcadoo.view.internal.api.ViewDefinition;
import com.qcadoo.view.internal.ribbon.model.InternalRibbonActionItem;
import com.qcadoo.view.internal.ribbon.model.InternalRibbonGroup;
import com.qcadoo.view.internal.ribbon.model.RibbonActionItemImpl;
import com.qcadoo.view.internal.ribbon.model.RibbonGroupImpl;

public class RibbonTemplates {

    private static final String ACTIONS = "actions";

    public InternalRibbonGroup getGroupTemplate(final String templateName, final ViewDefinition viewDefinition) {
        if ("navigation".equals(templateName)) {
            return createNavigationTemplate(viewDefinition);
        } else if ("gridNewAndRemoveAction".equals(templateName)) {
            return createGridNewAndRemoveActionsTemplate(viewDefinition);
        } else if ("gridNewCopyAndRemoveAction".equals(templateName)) {
            return createGridNewCopyAndRemoveActionTemplate(viewDefinition);
        } else if ("gridNewAndCopyAction".equals(templateName)) {
            return createGridNewAndCopyActionTemplate(viewDefinition);
        } else if ("formSaveCopyAndRemoveActions".equals(templateName)) {
            return createFormSaveCopyAndRemoveActionsTemplate(viewDefinition);
        } else if ("formSaveAndRemoveActions".equals(templateName)) {
            return createFormSaveAndRemoveActionsTemplate(viewDefinition);
        } else if ("formSaveAndBackAndRemoveActions".equals(templateName)) {
            return createFormSaveAndBackAndRemoveActionsTemplate(viewDefinition);
        } else if ("formSaveAction".equals(templateName)) {
            return createFormSaveActionTemplate(viewDefinition);
        } else {
            throw new IllegalStateException("Unsupported ribbon template : " + templateName);
        }
    }

    private InternalRibbonGroup createNavigationTemplate(final ViewDefinition viewDefinition) {
        InternalRibbonActionItem ribbonBackAction = new RibbonActionItemImpl();
        ribbonBackAction.setAction(RibbonUtils.translateRibbonAction("#{window}.performBack", viewDefinition));
        ribbonBackAction.setIcon("backIcon24.png");
        ribbonBackAction.setName("back");
        ribbonBackAction.setEnabled(true);
        ribbonBackAction.setType(RibbonActionItem.Type.BIG_BUTTON);

        InternalRibbonGroup ribbonGroup = new RibbonGroupImpl("navigation");
        ribbonGroup.addItem(ribbonBackAction);

        return ribbonGroup;
    }

    private InternalRibbonGroup createGridNewAndRemoveActionsTemplate(final ViewDefinition viewDefinition) {
        InternalRibbonGroup ribbonGroup = new RibbonGroupImpl(ACTIONS);
        ribbonGroup.addItem(createGridNewAction(viewDefinition));
        ribbonGroup.addItem(createGridDeleteAction(viewDefinition));
        return ribbonGroup;
    }

    private InternalRibbonGroup createGridNewAndCopyActionTemplate(final ViewDefinition viewDefinition) {
        InternalRibbonGroup ribbonGroup = new RibbonGroupImpl(ACTIONS);
        ribbonGroup.addItem(createGridNewAction(viewDefinition));
        ribbonGroup.addItem(createGridCopyAction(viewDefinition));
        return ribbonGroup;
    }

    private InternalRibbonGroup createGridNewCopyAndRemoveActionTemplate(final ViewDefinition viewDefinition) {
        InternalRibbonGroup ribbonGroup = new RibbonGroupImpl(ACTIONS);
        ribbonGroup.addItem(createGridNewAction(viewDefinition));
        ribbonGroup.addItem(createGridCopyAction(viewDefinition));
        ribbonGroup.addItem(createGridDeleteAction(viewDefinition));
        return ribbonGroup;
    }

    private InternalRibbonActionItem createGridDeleteAction(final ViewDefinition viewDefinition) {
        InternalRibbonActionItem ribbonDeleteAction = new RibbonActionItemImpl();
        ribbonDeleteAction.setAction(RibbonUtils.translateRibbonAction("#{grid}.performDelete;", viewDefinition));
        ribbonDeleteAction.setIcon("deleteIcon16.png");
        ribbonDeleteAction.setName("delete");
        ribbonDeleteAction.setType(RibbonActionItem.Type.SMALL_BUTTON);
        ribbonDeleteAction.setEnabled(false);
        ribbonDeleteAction.setDefaultEnabled(false);
        ribbonDeleteAction.setScript("var listener = {onChange: function(selectedArray) {if (selectedArray.length == 0) {"
                + "this.disable();} else {this.enable();}}}; #{grid}.addOnChangeListener(listener);");
        return ribbonDeleteAction;
    }

    private InternalRibbonActionItem createGridCopyAction(final ViewDefinition viewDefinition) {
        InternalRibbonActionItem ribbonCopyAction = new RibbonActionItemImpl();
        ribbonCopyAction.setAction(RibbonUtils.translateRibbonAction("#{grid}.performCopy;", viewDefinition));
        ribbonCopyAction.setIcon("copyIcon16.png");
        ribbonCopyAction.setName("copy");
        ribbonCopyAction.setEnabled(false);
        ribbonCopyAction.setDefaultEnabled(false);
        ribbonCopyAction.setScript("var listener = {onChange: function(selectedArray) {if (selectedArray.length == 0) {"
                + "this.disable();} else {this.enable();}}}; #{grid}.addOnChangeListener(listener);");
        ribbonCopyAction.setType(RibbonActionItem.Type.SMALL_BUTTON);
        return ribbonCopyAction;
    }

    private InternalRibbonActionItem createGridNewAction(final ViewDefinition viewDefinition) {
        InternalRibbonActionItem ribbonNewAction = new RibbonActionItemImpl();
        ribbonNewAction.setAction(RibbonUtils.translateRibbonAction("#{grid}.performNew;", viewDefinition));
        ribbonNewAction.setIcon("newIcon24.png");
        ribbonNewAction.setName("new");
        ribbonNewAction.setEnabled(true);
        ribbonNewAction.setType(RibbonActionItem.Type.BIG_BUTTON);
        return ribbonNewAction;
    }

    private InternalRibbonGroup createFormSaveCopyAndRemoveActionsTemplate(final ViewDefinition viewDefinition) {
        InternalRibbonGroup ribbonGroup = new RibbonGroupImpl(ACTIONS);
        ribbonGroup.addItem(createFormSaveAction(viewDefinition));
        ribbonGroup.addItem(createFormSaveAndBackAction(viewDefinition));
        ribbonGroup.addItem(createFormSaveAndNewAction(viewDefinition));
        ribbonGroup.addItem(createFormCopyAction(viewDefinition));
        ribbonGroup.addItem(createFormCancelAction(viewDefinition));
        ribbonGroup.addItem(createFormDeleteAction(viewDefinition));
        return ribbonGroup;
    }

    private InternalRibbonGroup createFormSaveAndRemoveActionsTemplate(final ViewDefinition viewDefinition) {
        InternalRibbonGroup ribbonGroup = new RibbonGroupImpl(ACTIONS);
        ribbonGroup.addItem(createFormSaveAction(viewDefinition));
        ribbonGroup.addItem(createFormSaveAndBackAction(viewDefinition));
        ribbonGroup.addItem(createFormCancelAction(viewDefinition));
        ribbonGroup.addItem(createFormDeleteAction(viewDefinition));
        return ribbonGroup;
    }

    private InternalRibbonGroup createFormSaveAndBackAndRemoveActionsTemplate(final ViewDefinition viewDefinition) {
        InternalRibbonGroup ribbonGroup = new RibbonGroupImpl(ACTIONS);
        ribbonGroup.addItem(createFormSaveAndBackAction(viewDefinition));
        ribbonGroup.addItem(createFormCancelAction(viewDefinition));
        ribbonGroup.addItem(createFormDeleteAction(viewDefinition));
        return ribbonGroup;
    }

    private InternalRibbonGroup createFormSaveActionTemplate(final ViewDefinition viewDefinition) {
        InternalRibbonActionItem ribbonSaveAction = new RibbonActionItemImpl();
        ribbonSaveAction.setAction(RibbonUtils
                .translateRibbonAction("#{form}.performSave; #{window}.performBack", viewDefinition));
        ribbonSaveAction.setIcon("saveBackIcon24.png");
        ribbonSaveAction.setName("save");
        ribbonSaveAction.setType(RibbonActionItem.Type.BIG_BUTTON);
        ribbonSaveAction.setEnabled(true);
        InternalRibbonGroup ribbonGroup = new RibbonGroupImpl(ACTIONS);
        ribbonGroup.addItem(ribbonSaveAction);

        return ribbonGroup;
    }

    private InternalRibbonActionItem createFormDeleteAction(final ViewDefinition viewDefinition) {
        InternalRibbonActionItem ribbonDeleteAction = new RibbonActionItemImpl();
        ribbonDeleteAction.setAction(RibbonUtils.translateRibbonAction("#{form}.performDelete; #{window}.performBack",
                viewDefinition));
        ribbonDeleteAction.setIcon("deleteIcon16.png");
        ribbonDeleteAction.setName("delete");
        ribbonDeleteAction.setType(RibbonActionItem.Type.SMALL_BUTTON);
        ribbonDeleteAction.setEnabled(false);
        ribbonDeleteAction.setDefaultEnabled(false);
        ribbonDeleteAction
                .setScript("var listener = {onSetValue: function(value) {if (!value || !value.content) return; if (value.content.entityId) {"
                        + "this.enable();} else {this.disable();}}}; #{form}.addOnChangeListener(listener);");
        return ribbonDeleteAction;
    }

    private InternalRibbonActionItem createFormCancelAction(final ViewDefinition viewDefinition) {
        InternalRibbonActionItem ribbonCancelAction = new RibbonActionItemImpl();
        ribbonCancelAction.setAction(RibbonUtils.translateRibbonAction("#{form}.performCancel;", viewDefinition));
        ribbonCancelAction.setIcon("cancelIcon16.png");
        ribbonCancelAction.setName("cancel");
        ribbonCancelAction.setEnabled(true);
        ribbonCancelAction.setType(RibbonActionItem.Type.SMALL_BUTTON);
        return ribbonCancelAction;
    }

    private InternalRibbonActionItem createFormCopyAction(final ViewDefinition viewDefinition) {
        InternalRibbonActionItem ribbonCopyAction = new RibbonActionItemImpl();
        ribbonCopyAction.setAction(RibbonUtils.translateRibbonAction("#{form}.performCopy;", viewDefinition));
        ribbonCopyAction.setIcon("copyIcon16.png");
        ribbonCopyAction.setName("copy");
        ribbonCopyAction.setType(RibbonActionItem.Type.SMALL_BUTTON);
        ribbonCopyAction.setEnabled(false);
        ribbonCopyAction.setDefaultEnabled(false);
        // ribbonCopyAction.setMessage("recordNotCreated");
        ribbonCopyAction
                .setScript("var listener = {onSetValue: function(value) {if (!value || !value.content) return; if (value.content.entityId) {"
                        + "this.enable();} else {this.disable();}}}; #{form}.addOnChangeListener(listener);");
        return ribbonCopyAction;
    }

    private InternalRibbonActionItem createFormSaveAndBackAction(final ViewDefinition viewDefinition) {
        InternalRibbonActionItem ribbonSaveBackAction = new RibbonActionItemImpl();
        ribbonSaveBackAction.setAction(RibbonUtils.translateRibbonAction("#{form}.performSave; #{window}.performBack;",
                viewDefinition));
        ribbonSaveBackAction.setIcon("saveBackIcon24.png");
        ribbonSaveBackAction.setName("saveBack");
        ribbonSaveBackAction.setEnabled(true);
        ribbonSaveBackAction.setType(RibbonActionItem.Type.BIG_BUTTON);
        return ribbonSaveBackAction;
    }

    private InternalRibbonActionItem createFormSaveAndNewAction(final ViewDefinition viewDefinition) {
        InternalRibbonActionItem ribbonSaveNewAction = new RibbonActionItemImpl();
        ribbonSaveNewAction.setAction(RibbonUtils.translateRibbonAction("#{form}.performSaveAndClear;", viewDefinition));
        ribbonSaveNewAction.setIcon("saveNewIcon16.png");
        ribbonSaveNewAction.setName("saveNew");
        ribbonSaveNewAction.setEnabled(true);
        ribbonSaveNewAction.setType(RibbonActionItem.Type.SMALL_BUTTON);
        return ribbonSaveNewAction;
    }

    private InternalRibbonActionItem createFormSaveAction(final ViewDefinition viewDefinition) {
        InternalRibbonActionItem ribbonSaveAction = new RibbonActionItemImpl();
        ribbonSaveAction.setAction(RibbonUtils.translateRibbonAction("#{form}.performSave;", viewDefinition));
        ribbonSaveAction.setIcon("saveIcon24.png");
        ribbonSaveAction.setName("save");
        ribbonSaveAction.setEnabled(true);
        ribbonSaveAction.setType(RibbonActionItem.Type.BIG_BUTTON);
        return ribbonSaveAction;
    }

}
