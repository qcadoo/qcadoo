package com.qcadoo.view.internal.components;

import com.qcadoo.view.api.components.CheckBoxComponent;

public class CheckBoxComponentState extends FieldComponentState implements CheckBoxComponent {

    private static final String CHECKED_VALUE = "1";

    public CheckBoxComponentState(final FieldComponentPattern pattern) {
        super(pattern);
    }

    @Override
    public void setChecked(boolean value) {
        if (value) {
            setFieldValue(CHECKED_VALUE);
        } else {
            setFieldValue(null);
        }
    }

    @Override
    public boolean isChecked() {
        return CHECKED_VALUE.equals(getFieldValue());
    }

}
