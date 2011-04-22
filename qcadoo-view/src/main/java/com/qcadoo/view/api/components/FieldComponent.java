package com.qcadoo.view.api.components;

import com.qcadoo.view.api.ComponentState;

public interface FieldComponent extends ComponentState {

    boolean isRequired();

    void setRequired(boolean required);

    void requestComponentUpdateState();

}