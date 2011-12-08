package com.qcadoo.view.api.utils;

import com.qcadoo.model.api.Entity;
import com.qcadoo.view.api.components.FormComponent;
import com.qcadoo.view.internal.components.form.FormComponentState;

public class FormUtils {

    private FormUtils() {
    }

    public final static void setFormEntity(final FormComponent form, final Entity entity) {
        ((FormComponentState) form).setEntity(entity);
    }
}
