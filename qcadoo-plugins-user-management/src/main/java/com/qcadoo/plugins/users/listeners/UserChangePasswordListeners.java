package com.qcadoo.plugins.users.listeners;

import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.CheckBoxComponent;
import com.qcadoo.view.api.components.FormComponent;
import com.qcadoo.view.constants.QcadooViewConstants;
import org.springframework.stereotype.Service;

@Service
public class UserChangePasswordListeners {

    private static final String L_CHANGED = "changed";

    public void changePassword(final ViewDefinitionState view, final ComponentState state, final String[] args) {
        FormComponent userForm = (FormComponent) view.getComponentByReference(QcadooViewConstants.L_FORM);
        CheckBoxComponent changedCheckBox = (CheckBoxComponent) view.getComponentByReference(L_CHANGED);

        userForm.performEvent(view, "save");

        if (!userForm.isHasError()) {
            changedCheckBox.setChecked(true);
        }
    }

}
